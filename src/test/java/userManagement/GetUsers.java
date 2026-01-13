package userManagement;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.text.IsEmptyString;
import org.jsoup.Connection;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetUsers{

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

}
