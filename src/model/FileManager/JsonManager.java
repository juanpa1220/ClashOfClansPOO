package model.FileManager;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonManager {

    public static JSONArray jsonReader(String path) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(path)) {
            JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);
            System.out.println(jsonArray);
            return jsonArray;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void jsonWriter(String filename, JSONObject jsonObject) throws Exception {
//        JSONObject sampleObject = new JSONObject();
//        sampleObject.put("name", "Stackabuser");
//        sampleObject.put("age", 35);
//
//        JSONArray messages = new JSONArray();
//        messages.add("Hey!");
//        messages.add("What's up?!");
//
//        sampleObject.put("messages", messages);

        Files.write(Paths.get(filename), jsonObject.toJSONString().getBytes());
    }

}
