package persistence;

import model.Event;
import model.EventLog;
import model.Expense;
import model.ListOfExpense;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

//Code influenced by the JsonSerializationDemo
//citation for these code: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

//represent reader that reads list of expenses from a json file that stores the data
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads list of expenses from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ListOfExpense read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Expenses are loaded."));
        return parseListOfExpense(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses list of expenses from JSON object and returns it
    private ListOfExpense parseListOfExpense(JSONObject jsonObject) {

        ListOfExpense loe = new ListOfExpense();
        addExpenses(loe, jsonObject);
        return loe;
    }

    // MODIFIES: loe
    // EFFECTS: parses expenses from JSON object and adds them to workroom
    private void addExpenses(ListOfExpense loe, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Expenses");
        for (Object json : jsonArray) {
            JSONObject nextExpense = (JSONObject) json;
            addExpense(loe, nextExpense);
        }
    }

    // MODIFIES: loe
    // EFFECTS: parses an expense from JSON object and adds it to workroom
    private void addExpense(ListOfExpense loe, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String monthYear = jsonObject.getString("monthYear");
        double amount = jsonObject.getDouble("amount");
        String category = jsonObject.getString("category");
        int score = jsonObject.getInt("score");
        double dollarScoreRatio = jsonObject.getDouble("dollarScoreRatio");

        Expense expense = new Expense(name, monthYear, amount, category);
        expense.setScore(score);
        expense.setDollarScoreRatio(dollarScoreRatio);

        loe.addExpenses(expense);
    }
}
