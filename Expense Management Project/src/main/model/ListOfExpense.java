package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.WriteToJson;

import java.util.ArrayList;
import java.util.List;

//Represent a list of expenses
public class ListOfExpense implements WriteToJson {
    List<Expense> listOfExpense;

    //EFFECTS: create an empty list of expenses
    public ListOfExpense() {
        listOfExpense = new ArrayList<>();

    }


    //MODIFIES: this
    //EFFECTS: add an expense to a list of expense
    public void addExpenses(Expense exp) {
        listOfExpense.add(exp);
        //Code influence by AlarmSystem
        //https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
        EventLog.getInstance().logEvent(new Event(exp.getName() + " is added to the list."));
    }

    //MODIFIES: this
    //EFFECTS: if the given index is inside the range of the list, remove the expense with given index in the list and
    // return true, otherwise return false
    public boolean removeExpense(int index) {
        if (index <= (listOfExpense.size() - 1) && index >= 0) {
            //Code influence by AlarmSystem
            //https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
            EventLog.getInstance().logEvent(new Event(listOfExpense.get(index).getName()
                    + " is removed from the list."));
            listOfExpense.remove(index);

            return true;
        }
        return false;
    }

    //REQUIRES: the category must be valid
    //EFFECTS: calculate and return the average score of satisfaction for a given category of expenses, do not count any
    //item whose score isn't given, and if no satisfaction score is given for a category, return 0
    public double calculateAvgScore(String category) {

        int count = 0;
        int total = 0;
        for (Expense exp : listOfExpense) {
            if (exp.matchCategory(category) && exp.getScore() != 0) {
                total += exp.getScore();
                count++;
            }
        }
        if (count == 0) {
            return 0;
        }
        //the way of converting integer to double is gained from practice midterm debugging question
        return (double) total / (double) count;
    }

    //REQUIRES: the time format must be valid
    //EFFECTS: calculate and return the total spending in a given time
    public double calculateAmountTotal(String time) {
        //Code influence by AlarmSystem
        //https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
        EventLog.getInstance().logEvent(new Event("User has calculated the total amount of spending at "
                + time));
        double total = 0;
        for (Expense exp : listOfExpense) {
            total += exp.spendAtTime(time);
        }
        return total;
    }


    public List<Expense> getListOfExpense() {
        return this.listOfExpense;
    }


    //Code influenced by the JsonSerializationDemo
    //citation for these code: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    //EFFECT: make this list of expenses into a json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Expenses", expensesToJson());
        return json;
    }

    //Code influenced by the JsonSerializationDemo
    //citation for these code: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    //EFFECT: return expenses in list of expenses as a json array
    public JSONArray expensesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Expense exp : listOfExpense) {
            jsonArray.put(exp.toJson());
        }

        return jsonArray;
    }
}
