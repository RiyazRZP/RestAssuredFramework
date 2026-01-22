package utils;

import io.restassured.path.json.JsonPath;
import org.apache.commons.io.FileUtils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

public class JsonReader {
    public static String getTestDataForDirectKeyRetrive(String input) throws IOException, ParseException, org.json.simple.parser.ParseException {
        String testData;
        return testData = (String) getJsonData().get(input);//input is the key

    }
    public static JSONObject getJsonData() throws IOException, ParseException, org.json.simple.parser.ParseException {

        //pass the path of the testdata.json file
        File filename = new File("resources//TestData//testdata.json");
        //convert json file into string
        String json = FileUtils.readFileToString(filename, "UTF-8");
        //parse the string into object
        Object obj = new JSONParser().parse(json);
        //give jsonobject o that I can return it to the function everytime it get called
        JSONObject jsonObject = (JSONObject) obj;
        return jsonObject;
    }


    public static String getTestData(String path) throws IOException {

        File filename = new File("resources/TestData/testdata.json");
        String json = FileUtils.readFileToString(filename, StandardCharsets.UTF_8);

        JsonPath js = new JsonPath(json);

        return js.getString(path);
    }
}
