package userManagement;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.BaseTest;
import core.StatusCode;
import io.restassured.RestAssured;
import io.restassured.http.*;
import utils.DataProviderUtil;

import io.restassured.response.Response;
import org.hamcrest.text.IsEmptyString;
import org.jsoup.Connection;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import utils.JsonReader;
import utils.PropertyReader;
import utils.SoftAssertionUtil;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.lang.reflect.Method;


public class GetUsers extends BaseTest {


    @Test
    public void getUserData(){
        given().header("x-api-key","reqres_2bd774c43f544db78b99ff2a7f2fc01e").
                when().get("https://reqres.in/api/users/1").
                then().assertThat().statusCode(200);
    }

    @Test
    public void getValidateResponseBody(){
        // Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        given().
                when().get("/todos/1").
                then().assertThat().statusCode(200).
                body(not(isEmptyString())).
                body("title", equalTo("delectus aut autem")).
                body("userId",equalTo(1));
        //or
        given().
                when().get("/todos/1").
                then().assertThat().statusCode(200).
                body(not(isEmptyString())).
                body(containsString("delectus aut autem")).
                body("userId",equalTo(1));
    }

    @Test
    public void getValidateResponseBody2(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given().
                when().get("/todos").
                then().extract().response();

        assertThat(response.getBody().asString(), containsString("quis ut nam facilis et officia qui"));
    }

    @Test
    public void getValidateResponseBody3(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given().
                when().get("/todos/2").
                then().extract().response();

        assertThat(response.getBody().jsonPath().get("id"), equalTo(2));
    }

    @Test
    public void getValidateResponseBodyHasItems(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given().
                when().get("/todos");

        assertThat(response.getBody().jsonPath().getList("title"), hasItems("quis ut nam facilis et officia qui","delectus aut autem"));
    }

    @Test
    public void getValidateResponseBodyHasSize(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given().
                when().get("/todos");

        assertThat(response.getBody().jsonPath().getList("title"), hasSize(200));
    }

    @Test
    public void getValidateResponseBodyUsingAllAssertion(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given()
                .when().
                get("/comments?postId=1");

        //AssertThat and response.then().body both are rest assured assertion:

        assertThat(response.getBody().jsonPath().getList("id"),hasItems(1));
        assertThat(response.getBody().jsonPath().getList("email"),hasItems("Eliseo@gardner.biz"));


        assertThat(response.getBody().jsonPath().get("id[1]"),is(2));
        assertThat(response.getBody().jsonPath().get("email[1]"),equalTo("Jayne_Kuhic@sydney.com"));

        response.then().body("id[4]",is(5)).body("email[4]",is("Hayden@althea.biz"));

        //testNG based assertion:
        String email = response.getBody().jsonPath().get("email[0]");
        Assert.assertEquals(email,"Eliseo@gardner.biz");

    }


    @Test
    public void getValidateResponseBodyUsingContains(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given()
                .when().
                get("/comments?postId=1").then().extract().response();

        assertThat(response.getBody().jsonPath().get("email"),
                contains("Eliseo@gardner.biz","Jayne_Kuhic@sydney.com","Nikita@garfield.biz","Lew@alysha.tv","Hayden@althea.biz"));

    }
    @Test
    public void getValidateResponseBodyUsingContains2(){
        Response response = given().baseUri("https://jsonplaceholder.typicode.com").queryParam("postId",1).
                when().get("/comments").
                then().extract().response();

        List<String> emailsValidate = Arrays.asList("Eliseo@gardner.biz", "Jayne_Kuhic@sydney.com", "Nikita@garfield.biz","Lew@alysha.tv","Hayden@althea.biz");
        assertThat(response.getBody().jsonPath().getList("email"),contains(emailsValidate.toArray(new String[0])));
    }

    @Test
    public void getValidateResponseBodyWholeResponse() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response = given()
                .when().
                get("/comments?postId=1").then().extract().response();

        int max = response.getBody().jsonPath().getList("id").size();

        for (int i = 0; i < max; i++) {
            int id = response.getBody().jsonPath().get("id[" + i + "]");
            int postId = response.getBody().jsonPath().get("postId["+ i +"]");
            String email = response.getBody().jsonPath().get("email["+ i +"]");
            String body = response.getBody().jsonPath().get("body["+ i +"]");

            response.then().body("id["+ i + "]",is(id)).
                    body("postId["+ i +"]",is(postId)).body("email[" + i +"]",is(email)).
                    body("body[" + i + "]",is(body));
        }

        //here we just need a external data files to validate the response what we get from the api call..
    }

    @Test
    public void getValidateResponseBodyQueryParam(){
        given().
                baseUri("https://jsonplaceholder.typicode.com").
                queryParam("postId",2).
                when().get("/comments").
                then().body("postId[0]",is(2)).body(containsString("repellat consequatur praesentium vel minus molestias voluptatum"));

        //if we right only containsString without the key name means it check on whole response body.
    }
    @Test
    public void getValidateResponseBodyMultipleQueryParam(){
        given().
                baseUri("https://jsonplaceholder.typicode.com").
                queryParam("postId",2).
                queryParam("id",6).
                when().get("/comments").
                then().body("id[0]",is(6)).body("postId[0]",is(2));
    }
    @Test
    public void getValidateResponseBodyUsingPathParam(){
        Response resp = given().baseUri("https://jsonplaceholder.typicode.com").pathParams("id",1).
                when().get("/comments/{id}").
                then().extract().response();

        resp.then().body("name",is("id labore ex et quam laborum"));

        resp.then().body(containsString("id labore ex et quam laborum"));

        System.out.println(resp.body().asString());  //To print response on the console

        //we can use multiple path param as well..
    }


    @Test
    public void testCreateCommentPostRequestWithFormParam(){
        Response resp = given().
                baseUri("https://jsonplaceholder.typicode.com").
                contentType("application/x-www-form-urlencoded; charset=UTF-8").
                formParam("postId",50001).
                formParam("name","Faiyaz").
                formParam("email","faiyaz@gmail.com").
                formParam("body","This is the body").
                when()
                .post("/comments");
        resp.then().statusCode(201).body("name",is("Faiyaz"));
    }
    @Test
    public void testCreateCommentPostRequestWithRawBodyJson(){
        Response resp = given().
                            baseUri("https://jsonplaceholder.typicode.com").
                            contentType("application/json").body("{\n" +
                                    "    \"postId\": \"50001\",\n" +
                                    "    \"name\": \"Riyaz\",\n" +
                                    "    \"email\": \"riyaz@gmail.com\",\n" +
                                    "    \"body\": \"This is my post request post \"\n" +
                                    "}").
                        when()
                            .post("/comments");

        resp.then().statusCode(201).body("name",is("Riyaz"));
    }
    @Test
    public void testGetUserUsingOneHeader(){
        Response resp = given().baseUri("https://reqres.in/api").
                header("x-api-key","reqres_2bd774c43f544db78b99ff2a7f2fc01e").
                header("contentType","application/json").
                when().get("/users").
                then().extract().response();
        //suppose you have response in array of object with name use below data.id means get list of all id's.
        List<Integer> id = resp.getBody().jsonPath().getList("data.id");
        assertThat(id,hasSize(6));

        //we can use single headers and multiple header as well...
    }
    @Test
    public void testGetUsers(){
        //array don't have a name
        Response resp = given().baseUri("https://jsonplaceholder.typicode.com").
                queryParam("postId",2).
                when().get("/comments").
                then().extract().response();

        //suppose you have response in array of object without a name means get list of all id's.
        List<Integer> id = resp.getBody().jsonPath().getList("id");
        assertThat(id,hasSize(5));
    }
    @Test
    public void getUsersWithHeadersMap() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("x-api-key", "reqres_2bd774c43f544db78b99ff2a7f2fc01e");
        headers.put("contentType", "application/json");

        Response resp = given()
                .baseUri("https://reqres.in/api").
                headers(headers).
                when().get("/users");

        //validating headers from response..
        Headers responseHeaders = resp.getHeaders();
        for(Header h : responseHeaders){
            if(h.getName().contains("Server")){
                Assert.assertEquals(h.getValue(),"cloudflare");
            }
        }
    }
    @Test
    public void getUserWithSendingCookies(){
        Map<String,String> cookiesMap = new HashMap<String,String>();
        cookiesMap.put("cookieName","cookieValue");
        cookiesMap.put("cookieName2","cookieValue");

        Response resp = given()
                .header("x-api-key","reqres_2bd774c43f544db78b99ff2a7f2fc01e")
                .when().get("https://reqres.in/api/users?page=2");

        //If we use map below is the way to write
        Map<String,String> getCookies = resp.getCookies();
        assertThat(getCookies,hasKey("cookieName1"));
        assertThat(getCookies,hasValue("cookieValue1"));

       //or

        //if we use cookies class, below is the way to write.
        Cookies getCookies2 = resp.getDetailedCookies();
        Cookie cook = getCookies2.get("cookieName2");
        Assert.assertEquals(cook.getValue(),"cookieValue2");
    }

    @Test
    public void testWithBasicAuth(){
        Response resp = given()
                .auth()
                .basic("postman","password")
                .when()
                .get("https://postman-echo.com/basic-auth");

        resp.then().statusCode(200).body("authenticated",is(true));
    }

    @Test
    public void testWithDigestAuth(){
        Response resp = given()
                .auth()
                .digest("postman","password")
                .when()
                .get("https://postman-echo.com/digest-auth");

        resp.then().statusCode(200).body("authenticated",is(true));
    }

    //get token method:
    public static String getAccessToken() {

        Response resp =
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .formParam("client_id", "CodeWithSid")
                        .formParam("client_secret", "367e597314195b3d58faaa71ccaddfd0")
                        .formParam("grant_type", "client_credentials")
                        .when()
                        .post("http://coop.apps.symfonycasts.com/token")
                        .then()
                        .statusCode(200)
                        .extract().response();

        String token = resp.jsonPath().getString("access_token");
        System.out.println("Token: " + token);

        return token;
    }

    @Test
    public void testApiChickenFeedPost(){
        String token = getAccessToken();
        Response resp = given()
                .auth()
                .oauth2(token)                                                //.header("Authorization", "Bearer " + token)
                .when()
                .post("http://coop.apps.symfonycasts.com/api/4490/chickens-feed")
                .then()
                .extract()
                .response();

        resp.then().statusCode(200).body("message",containsString("Your chickens are now full and happy"));
    }

    //retriving the value from array Json.
    @Test
    public void testDeleteRequest0() throws IOException {
        Response resp = given()
                .header(JsonReader.getTestData("testDeleteRequest[0].apiKey_name"),JsonReader.getTestData("testDeleteRequest[0].apiValue_name"))
                .pathParam(JsonReader.getTestData("testDeleteRequest[0].parameter"),JsonReader.getTestData("testDeleteRequest[0].id_value"))
                .when().delete("https://reqres.in/api/users/{id}");
        Assert.assertEquals(resp.getStatusCode(), StatusCode.NO_CONTENT.code);
        System.out.println(StatusCode.NO_CONTENT.code+" " +StatusCode.NO_CONTENT.message);
    }

    //retriving the value from json which key name to one set of json.
    @Test
    public void testDeleteRequest2() throws IOException, ParseException, org.json.simple.parser.ParseException {
        System.out.println(JsonReader.getTestData("testDeleteRequest2"));
        Response resp = given()
                .header(JsonReader.getTestData("testDeleteRequest2.apiKey_name"),JsonReader.getTestData("testDeleteRequest2.apiValue_name"))
                .pathParam(JsonReader.getTestData("testDeleteRequest2.parameter"),JsonReader.getTestData("testDeleteRequest2.id_value"))
                .when().delete("https://reqres.in/api/users/{id}");
        Assert.assertEquals(resp.getStatusCode(), StatusCode.NO_CONTENT.code);
        System.out.println(StatusCode.NO_CONTENT.code+" " +StatusCode.NO_CONTENT.message);
    }

    //here we have retrived from both Jsonfile and properties files. just for git..
    @Test
    public void testDeleteRequest3() throws IOException, ParseException, org.json.simple.parser.ParseException {
        System.out.println(JsonReader.getTestData("testDeleteRequest2"));
        String URL = PropertyReader.propertyReader("config.properties","baseURL")+JsonReader.getTestData("testDeleteRequest[0].delete_endpoint");
        System.out.println(URL);
        Response resp = given()
                .header(JsonReader.getTestData("testDeleteRequest2.apiKey_name"),JsonReader.getTestData("testDeleteRequest2.apiValue_name"))
                .pathParam(JsonReader.getTestData("testDeleteRequest2.parameter"),JsonReader.getTestData("testDeleteRequest2.id_value"))
                .when().delete(URL);
        SoftAssertionUtil.assertEqual(StatusCode.NO_CONTENT.code,resp.getStatusCode(),"The status is not 204");
        SoftAssertionUtil.assertAll();
        System.out.println(StatusCode.NO_CONTENT.code+" " +StatusCode.NO_CONTENT.message);
    }

    @Test(dataProvider="apiDp", dataProviderClass = DataProviderUtil.class)
    @Parameters({"id","name"})
    public void testWithDpAndPara(String id,String name) {
        System.out.println(JsonReader.getTestData("testDeleteRequest2.apiKey_name"));
        Response resp = given()
                .header(JsonReader.getTestData("testDeleteRequest[0].apiKey_name"),JsonReader.getTestData("testDeleteRequest[0].apiValue_name"))    //If it is array be specific, if it is not no need.
                .queryParam("id",id)
                .queryParam("name",name)
                .when()
                .get(PropertyReader.propertyReader("config.properties","baseURL")+"/users");

        SoftAssertionUtil.assertEqual(resp.getStatusCode(),StatusCode.SUCCESS.code,"The status is not 200");
        SoftAssertionUtil.assertAll();
    }
    @Test(dataProvider = "dpJson", dataProviderClass = DataProviderUtil.class, groups = {"smoke"})
    public void testWithDpWithJson(Map<String,Object> result) {
        System.out.println(result);
        System.out.println(JsonReader.getTestData("testDeleteRequest[0].apiKey_name"));  //it is array of json object it have not used indexing here so it retrived me all matched keys here.
        Response resp = given()
                .header(JsonReader.getTestData("testDeleteRequest[0].apiKey_name"),JsonReader.getTestData("testDeleteRequest[0].apiValue_name"))    //If it is array be specific use indexing, if it is not an array no need.
                .queryParam("id",result.get("id"))
                .queryParam("name",result.get("name"))
                .when()
                .get(PropertyReader.propertyReader("config.properties","baseURL")+"/users");

        SoftAssertionUtil.assertEqual(resp.getStatusCode(),StatusCode.SUCCESS.code,"The status is not 200");
        SoftAssertionUtil.assertAll();
    }

    @Test
    public void getRequestWithFilter(){

        Map<String,String> headers = new HashMap<String,String>();
        headers.put("x-api-key", "reqres_2bd774c43f544db78b99ff2a7f2fc01e");

        Response resp = given()
                .headers(headers)
                .when()
                .get(PropertyReader.propertyReader("config.properties","baseURL")+"/users");

        Assert.assertEquals(resp.getBody().jsonPath().get("data.find { it.first_name == 'George' }.email"), "george.bluth@reqres.in");
        //resp.then().body("data.find { it.first_name == 'George' }.email"), is("george.bluth@reqres.in");
        System.out.println("get is execueted");
        System.out.println(resp.getBody().asString());
    }

}
