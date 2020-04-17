import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SomeChecks {
    public static void main(String[] args) {
        FileReader reader = null;
        try {
            reader = new FileReader("/home/deni/Documents/Learnings/AlgoExpert/src/com/learning/algo/expert/emp_details.json");
            parseObjects(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public static void parseObjects(FileReader fileData) {
        if (fileData == null) return;
        Gson gson = new GsonBuilder().create();
        JsonObject[] empDetails = gson.fromJson(fileData, JsonObject[].class);
        for (JsonObject each : empDetails) {
            System.out.println();
            System.out.println(each.get("name"));
            System.out.println(each.get("age"));
            System.out.println(each.get("company"));
        }
    }
}
