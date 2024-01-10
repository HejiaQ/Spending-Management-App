package persistence;

import model.Expense;
import model.ListOfExpense;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//Code influenced by the JsonSerializationDemo
//citation for these code: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

//Test the Json writer
public class JsonWriterTest extends JsonTest{
    @Test
    void testWriterInvalidFile() {
        try {
            ListOfExpense loe = new ListOfExpense();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //test pass
        }
    }

    @Test
    void testWriterEmptyExpenses() {
        try {
            ListOfExpense loe = new ListOfExpense();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyExpenses.json");
            writer.open();
            writer.write(loe);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyExpenses.json");
            loe = reader.read();
            assertEquals(0, loe.getListOfExpense().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralExpenses() {
        try {
            ListOfExpense loe = new ListOfExpense();
            Expense exp1 = new Expense("Chocolate", "03/2023", 10.98, "Food");
            exp1.setScore(5);
            exp1.calculateRatio();

            Expense exp2 = new Expense("Book<Orlando>", "06/2023", 103, "Study");

            loe.addExpenses(exp1);
            loe.addExpenses(exp2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralExpenses.json");
            writer.open();
            writer.write(loe);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralExpenses.json");
            loe = reader.read();
            List<Expense> expenses = loe.getListOfExpense();
            assertEquals(2, expenses.size());
            checkExpense("Chocolate", "03/2023", 10.98, "Food", 5,
                    10.98/ (double) 5, expenses.get(0));
            checkExpense("Book<Orlando>", "06/2023", 103, "Study", 0,
                    0, expenses.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
