package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//test the expense
class ExpenseTest {
    private Expense testExpense;

    @BeforeEach
    void runBefore() {
        testExpense = new Expense("Books<Orlando>", "04/2023", 50.9, "Study");
    }


    @Test
    void constructorTest() {
        assertEquals(50.9, testExpense.getAmount());
        assertEquals("Books<Orlando>", testExpense.getName());
        assertEquals("Study", testExpense.getCategory());
        assertEquals("04/2023", testExpense.getMonthYear());
    }

    @Test
    void spendAtTimeTest() {
        double d1 = testExpense.spendAtTime("02/2023");
        double d2 = testExpense.spendAtTime("04/2023");

        assertEquals(0, d1);
        assertEquals(50.9,d2);

    }


    //way of involving integer in calculation but get a double is learned form practice midterm debugging question
    @Test
    void calculateSatisfyPerDollarTest() {
        boolean d1 = testExpense.calculateRatio();
        assertFalse(d1);

        testExpense.setScore(4);
        boolean d2 = testExpense.calculateRatio();
        assertTrue(d2);
        assertEquals(50.9 / (double) 4, testExpense.getDollarScoreRatio());



    }

    @Test
    void matchCategoryTest() {
        boolean b1 = testExpense.matchCategory("Food");
        boolean b2 = testExpense.matchCategory("Study");

        assertFalse(b1);
        assertTrue(b2);
    }

}