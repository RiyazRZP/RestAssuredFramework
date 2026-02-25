package userManagement;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JsonSchemaValiation {

    @Test
    public void jsonSchemaValidation(){
        File schema = new File(System.getProperty("user.dir")+"/resources/testData/JsonSchema.json");
        Response resp = given()
                .header("x-api-key","reqres_2bd774c43f544db78b99ff2a7f2fc01e")
                .header("content-Type","application/json")
                .when()
                .get("https://reqres.in/api/users");

        resp.then().body(JsonSchemaValidator.matchesJsonSchema(schema));
    }
}
