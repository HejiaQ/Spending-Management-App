package persistence;

//Code influenced by the JsonSerializationDemo
//citation for these code: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import model.Expense;

import static org.junit.jupiter.api.Assertions.assertEquals;

//help test json by checking whether expense has right information
public class JsonTest {
    protected void checkExpense(String name, String monthYear, double amount, String category,
                                int score, double dollarScoreRatio, Expense expense) {

        assertEquals(name, expense.getName());
        assertEquals(monthYear, expense.getMonthYear());
        assertEquals(amount, expense.getAmount());
        assertEquals(category, expense.getCategory());
        assertEquals(score, expense.getScore());
        assertEquals(dollarScoreRatio, expense.getDollarScoreRatio());




    }
}
