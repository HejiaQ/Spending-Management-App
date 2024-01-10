package persistence;

import model.Event;
import model.EventLog;
import model.ListOfExpense;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


//Code influenced by the JsonSerializationDemo
//citation for these code: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

//Represent a writer that writes the data to a json file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destinationFile;

    // EFFECTS: constructs writer to write to destinationFile file
    public JsonWriter(String destinationFile) {
        this.destinationFile = destinationFile;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destinationFile file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destinationFile));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of list of expenses to file
    public void write(ListOfExpense loe) {
        EventLog.getInstance().logEvent(new Event("Expenses are saved."));
        JSONObject json = loe.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
