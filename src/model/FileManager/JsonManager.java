package model.FileManager;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonManager {

    public static JSONArray jsonReader(String path) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(path)) {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);
            return jsonArray;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean jsonWriter(JSONObject jsonObject) throws Exception {

        String filename = "src/asserts/docs/gameSettings/warriors.json";
        JSONArray oldFile = jsonReader(filename);

        if (oldFile != null) {
            JSONArray temObject = new JSONArray();
            for (Object obj : oldFile) {
                temObject.add(obj);
            }
            temObject.add(jsonObject);

            try (FileWriter file = new FileWriter(filename)) {
                file.write(temObject.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }
        return false;
    }

}
