package model;

import org.json.JSONObject;
import persistence.WriteToJson;

//Represent an expense that has information about what the expense is for (name), time of the expense, amount of
//expense, category of expense, score of satisfaction of the expense which should ranges from 1 to 5, with a
//default of 0, and dollar to score ratio of 0.
public class Expense implements WriteToJson {
    private String name;
    private String monthYear;
    private double amount;
    private String category;
    private int score;
    private double dollarScoreRatio;


    //REQUIRES: amount of money need to be positive, time of expense need to be in right format, category need to be
    //inside the valid category which specified by user instruction
    //EFFECTS: create an expense with given name, time of expense, amount of expense, the category of the expense, and
    //also a default scores
    public Expense(String name, String time, double amount, String category) {
        this.name = name;
        this.monthYear = time;
        this.amount = amount;
        this.category = category;
        this.score = 0;
        this.dollarScoreRatio = 0;

    }



    //REQUIRES: time given need to be in format of MM/YEAR, for example "08/2004"
    //EFFECTS: return amount of money spent if the expense time matches the given time, return 0 if they don't match
    public double spendAtTime(String time) {
        if (this.monthYear.equals(time)) {
            return this.amount;
        }
        return 0;
    }

    //MODIFIES: this
    //EFFECTS: if the score is set by the user, set dollar to score ratio of this expense to how much dollar each score
    //correspond to, which is the ratio of the spending and the satisfaction score, and return true; if score has not
    //been set yet, return false.
    public boolean calculateRatio() {
        if (score != 0) {
            this.dollarScoreRatio = this.amount / this.score;
            return true;
        }
        return false;
    }

    //REQUIRES: category given need to be inside the valid category which specified by user instruction
    //EFFECTS: return true if the expense category matches the given category, false otherwise.
    public boolean matchCategory(String category) {
        return this.category.equals(category);
    }



    public void setScore(int score) {
        this.score = score;

    }

    public void setDollarScoreRatio(double dsr) {
        this.dollarScoreRatio = dsr;
    }

    public String getName() {
        return this.name;
    }

    public String getMonthYear() {
        return this.monthYear;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getCategory() {
        return this.category;
    }

    public int getScore() {
        return this.score;
    }

    public double getDollarScoreRatio() {
        return this.dollarScoreRatio;
    }


    //Code influenced by the JsonSerializationDemo
    //citation for these code: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    //EFFECT: make expense into a json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("monthYear", monthYear);
        json.put("amount", amount);
        json.put("category", category);
        json.put("score", score);
        json.put("dollarScoreRatio", dollarScoreRatio);
        return json;
    }
}
