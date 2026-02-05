package userManagement;

import core.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.FailRetry;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RetryApi extends BaseTest {

    @Test
    public void putRequestWithString(){

        Map<String,String> headers = new HashMap<String,String>();
        headers.put("x-api-key", "reqres_2bd774c43f544db78b99ff2a7f2fc01e");
        headers.put("content-Type", "application/json");

        Response resp = given()
                .headers(headers)
                .pathParam("id",1)
                .body("{\"email\":\"riyazpasha@gmail.com\",\"first_name\":\"Riyaz\",\"last_name\":\"Mohammed\"}")
                .when()
                .put("https://reqres.in/api/users/{id}");

        Assert.assertEquals(resp.getStatusCode(),2000);
        System.out.println("putRequestWithString is execueted");
        System.out.println(resp.getBody().asString());
    }

}
