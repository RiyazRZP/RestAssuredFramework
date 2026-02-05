package userManagement;

import core.BaseTest;
import core.StatusCode;
import io.restassured.response.Response;
import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.PropertyReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class postPutPatchApisUsingStringFile extends BaseTest {

     private static FileInputStream getRequestBodyFromJsonFile(String fileName){
         //Files.newInputStream(new File(System.getProperty("user.dir") + "/Resources/TestData/patchRequestBody.json").toPath())
         FileInputStream fileInputStream;
         try {
             fileInputStream = new FileInputStream(new File(System.getProperty("user.dir") + "/Resources/TestData/"+fileName));
         } catch (FileNotFoundException e) {
             throw new RuntimeException(e);
         }
         return fileInputStream;
     }
     
    // read request body by String
    @Test
    public void postRequestWithString(){

        Map<String,String> headers = new HashMap<String,String>();
        headers.put("x-api-key", "reqres_2bd774c43f544db78b99ff2a7f2fc01e");
        headers.put("content-Type", "application/json");

        Response resp = given()
                .headers(headers)
                .body("{\"email\":\"riyazpasha@gmail.com\",\"first_name\":\"Riyaz\",\"last_name\":\"Pashaa\"}")
                .when()
                .post("https://reqres.in/api/users");

        Assert.assertEquals(resp.getStatusCode(),201);
        System.out.println("postRequestWithString is execueted");
        System.out.println(resp.getBody().asString());
    }

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

        Assert.assertEquals(resp.getStatusCode(),200);
        System.out.println("putRequestWithString is execueted");
        System.out.println(resp.getBody().asString());
    }
    @Test
    public void patchRequestWithString(){

        Map<String,String> headers = new HashMap<String,String>();
        headers.put("x-api-key", "reqres_2bd774c43f544db78b99ff2a7f2fc01e");
        headers.put("content-Type", "application/json");

        Response resp = given()
                .headers(headers)
                .pathParam("id",1)
                .body("{\"first_name\":\"Faiyaz\"}")
                .when()
                .patch(PropertyReader.propertyReader("config.properties","baseURL")+"/users/{id}");

        Assert.assertEquals(resp.getStatusCode(), StatusCode.SUCCESS.code);
        System.out.println("patchRequestWithString is execueted");
        System.out.println(resp.getBody().asString());
    }

    //Read request body by Json file

    //body(IOUtils.toString(new FileInputStream(new File(System.getProperty("user.dir") + "/Resources/TestData/postRequestBody.json"))))

    @Test
    public void postRequestWithJsonFile() throws IOException {
        Map<String,String> headers = new HashMap<String,String>();
        headers.put("x-api-key", "reqres_2bd774c43f544db78b99ff2a7f2fc01e");
        headers.put("content-Type", "application/json");

        Response resp = given()
                .headers(headers)
                .pathParam("id",1)
                .body(IOUtils.toString(getRequestBodyFromJsonFile("postRequestBody.json")))
                .when()
                .post(PropertyReader.propertyReader("config.properties","baseURL")+"/users/{id}");

        Assert.assertEquals(resp.getStatusCode(),StatusCode.CREATED.code);
        System.out.println("postRequestWithJsonFile is executed");
        System.out.println(resp.getBody().asString());
    }
    @Test
    public void putRequestWithJsonFile() throws IOException {
        Map<String,String> headers = new HashMap<String,String>();
        headers.put("x-api-key", "reqres_2bd774c43f544db78b99ff2a7f2fc01e");
        headers.put("content-Type", "application/json");

        Response resp = given()
                .headers(headers)
                .pathParam("id",1)
                .body(IOUtils.toString(getRequestBodyFromJsonFile("putRequestBody.json")))
                .when()
                .put(PropertyReader.propertyReader("config.properties","baseURL")+"/users/{id}");

        Assert.assertEquals(resp.getStatusCode(),StatusCode.SUCCESS.code);
        System.out.println("putRequestWithJsonFile is executed");
        System.out.println(resp.getBody().asString());
    }
    @Test
    public void patchRequestWithJsonFile() throws IOException {
        Map<String,String> headers = new HashMap<String,String>();
        headers.put("x-api-key", "reqres_2bd774c43f544db78b99ff2a7f2fc01e");
        headers.put("content-Type", "application/json");

        Response resp = given()
                .headers(headers)
                .pathParam("id",1)
                .body(IOUtils.toString(getRequestBodyFromJsonFile("patchRequestBody.json")))
                .when()
                .patch(PropertyReader.propertyReader("config.properties","baseURL")+"/users/{id}");

        Assert.assertEquals(resp.getStatusCode(),StatusCode.SUCCESS.code);
        System.out.println("patchRequestWithJsonFile is executed");
        System.out.println(resp.getBody().asString());
    }


}
