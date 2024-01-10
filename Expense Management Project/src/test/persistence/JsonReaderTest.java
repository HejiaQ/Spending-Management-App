package persistence;

//Code influenced by the JsonSerializationDemo
//citation for these code: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import model.Expense;
import model.ListOfExpense;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//Test the Json reader
public class JsonReaderTest extends JsonTest{
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/fileDoNotExist.json");
        try {
            ListOfExpense loe = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // test pass
        }
    }

    @Test
    void testReaderEmptyExpenses() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyExpenses.json");
        try {
            ListOfExpense loe = reader.read();
            assertEquals(0, loe.getListOfExpense().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralExpenses() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralExpenses.json");
        try {
            ListOfExpense loe = reader.read();
            List<Expense> listOfExpense = loe.getListOfExpense();
            assertEquals(2, listOfExpense.size());
            checkExpense("Sushi", "03/2023", 10.98, "Food", 5,
                    10.98/ (double) 5, listOfExpense.get(0));
            checkExpense("Book<Orlando>", "06/2023", 103, "Study", 0,
                    0, listOfExpense.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
