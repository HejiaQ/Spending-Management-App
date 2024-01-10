package persistence;

import org.json.JSONObject;

//Code influenced by the JsonSerializationDemo
//citation for these code https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/pulse

//represent a tool to turn something into json object
public interface WriteToJson {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
