package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//test the list of expense
public class ListOfExpenseTest {
    private ListOfExpense loeTest;
    private Expense e1;
    private Expense e2;
    private Expense e3;

    @BeforeEach
    void runBefore() {
        loeTest = new ListOfExpense();
        e1 = new Expense("Bananas", "05/2022", 10.99, "Food");
        e2 = new Expense("Table", "03/2020", 120, "Furniture");
        e3 = new Expense("Chocolate", "03/2020", 8.89, "Food");
    }

    @Test
    void constructorTest() {
       boolean empty = loeTest.getListOfExpense().isEmpty();
       assertTrue(empty);

    }

    @Test
    void addSingleExpenseTest() {
        loeTest.addExpenses(e1);
        assertEquals(1, loeTest.getListOfExpense().size());
        assertEquals(e1, loeTest.getListOfExpense().get(0));
    }

    @Test
    void addMoreExpenseTest() {
        loeTest.addExpenses(e1);
        loeTest.addExpenses(e2);
        assertEquals(2, loeTest.getListOfExpense().size());
        assertEquals(e1, loeTest.getListOfExpense().get(0));
        assertEquals(e2, loeTest.getListOfExpense().get(1));

    }

    @Test
    void removeSingleExpenseTest() {
        loeTest.addExpenses(e1);
        loeTest.addExpenses(e2);
        loeTest.addExpenses(e3);
        boolean b1 = loeTest.removeExpense(1);

        assertTrue(b1);
        assertEquals(2, loeTest.getListOfExpense().size());
        assertEquals(e1, loeTest.getListOfExpense().get(0));
        assertEquals(e3, loeTest.getListOfExpense().get(1));
        assertFalse(loeTest.getListOfExpense().contains(e2));



    }

    @Test
    void removeMoreExpenseTest() {
        loeTest.addExpenses(e1);
        loeTest.addExpenses(e2);
        loeTest.addExpenses(e3);
        boolean b1 = loeTest.removeExpense(0);
        boolean b2 = loeTest.removeExpense(1);

        assertTrue(b1);
        assertTrue(b2);
        assertEquals(1, loeTest.getListOfExpense().size());
        assertEquals(e2, loeTest.getListOfExpense().get(0));
        assertFalse(loeTest.getListOfExpense().contains(e1));
        assertFalse(loeTest.getListOfExpense().contains(e3));
    }

    @Test
    void removeFalseTest() {
        loeTest.addExpenses(e1);
        loeTest.addExpenses(e2);
        loeTest.addExpenses(e3);
        boolean b1 = loeTest.removeExpense(3);
        boolean b2 = loeTest.removeExpense(-1);

        assertFalse(b1);
        assertFalse(b2);
        assertEquals(3, loeTest.getListOfExpense().size());
        assertEquals(e1, loeTest.getListOfExpense().get(0));
        assertEquals(e2, loeTest.getListOfExpense().get(1));
        assertEquals(e3, loeTest.getListOfExpense().get(2));

    }
    @Test
    void calculateAvgScoreTest () {
        e1.setScore(5);
        e2.setScore(3);
        e3.setScore(4);
        loeTest.addExpenses(e1);
        loeTest.addExpenses(e2);
        loeTest.addExpenses(e3);
        double avg = loeTest.calculateAvgScore("Food");
        double avg2 = loeTest.calculateAvgScore("Furniture");
        assertEquals(4.5, avg);
        assertEquals(3, avg2);

    }

    @Test
    void someScoreNotSetTest(){
        e1.setScore(5);
        e2.setScore(3);
        loeTest.addExpenses(e1);
        loeTest.addExpenses(e2);
        loeTest.addExpenses(e3);
        double avg = loeTest.calculateAvgScore("Food");
        assertEquals(5, avg);
    }

    @Test
    void scoreNotSetTest(){
        loeTest.addExpenses(e1);
        loeTest.addExpenses(e2);
        loeTest.addExpenses(e3);
        double avg = loeTest.calculateAvgScore("Food");
        assertEquals(0, avg);
    }

    @Test
    void noDataForCategoryTest(){
        e1.setScore(5);
        e2.setScore(3);
        e3.setScore(4);
        loeTest.addExpenses(e1);
        loeTest.addExpenses(e2);
        loeTest.addExpenses(e3);
        double avg = loeTest.calculateAvgScore("Study");
        assertEquals(0, avg);
    }

    @Test
    void calculateAmountTotalTest() {
        loeTest.addExpenses(e1);
        loeTest.addExpenses(e2);
        loeTest.addExpenses(e3);

        double amt1 = loeTest.calculateAmountTotal("03/2020");
        double amt2 = loeTest.calculateAmountTotal("05/2022");

        assertEquals(128.89, amt1);
        assertEquals(10.99, amt2);
    }

    @Test
    void noAmountTest() {
        loeTest.addExpenses(e1);
        loeTest.addExpenses(e2);
        loeTest.addExpenses(e3);

        double amt1 = loeTest.calculateAmountTotal("03/2021");
        double amt2 = loeTest.calculateAmountTotal("09/2022");

        assertEquals(0, amt1);
        assertEquals(0, amt2);

    }

}
