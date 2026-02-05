package userManagement;

import core.StatusCode;
import io.restassured.response.Response;
import junit.framework.Assert;
import org.testng.annotations.Test;
import pojo.MyTemperature;
import pojo.PostRequestBody;
import pojo.PostResponseBody;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.post;

public class PostPutPatchUsingPOJO {

    @Test
    public void postRequestPOJO(){
        PostRequestBody postrequestBody = getPostRequestBody();


        Response resp = given()
                .header("x-api-key","reqres_2bd774c43f544db78b99ff2a7f2fc01e")
                .header("content-Type","application/json")
                .body(postrequestBody)
                .when()
                .post("https://reqres.in/api/users");

        Assert.assertEquals(resp.getStatusCode(), StatusCode.CREATED.code);
        PostResponseBody postResponseBody = resp.as(PostResponseBody.class);
        List<MyTemperature> temps = postResponseBody.getTemp();
        temps.stream().forEach(e-> System.out.println(e.getTemp()+" "+e.getCity()));
        System.out.println(postResponseBody);
    }
    //made a method for getting request body...
    private static PostRequestBody getPostRequestBody() {
        PostRequestBody postrequestBody = new PostRequestBody();
        postrequestBody.setEmail("rzp@gamil.com");
        postrequestBody.setFirst_name("rzp");
        postrequestBody.setLast_name("pasha");

        List<String> city = new ArrayList<String>();
        city.add("Hyderabad");
        city.add("Bangalore");

        postrequestBody.setCity(city);

        MyTemperature myTemperature1 = new MyTemperature();
        myTemperature1.setCity("Hyderabad");
        myTemperature1.setTemp(30);
        MyTemperature myTemperature2 = new MyTemperature();
        myTemperature2.setCity("Bangalore");
        myTemperature2.setTemp(25);

        List<MyTemperature> myTemp = new ArrayList<MyTemperature>();
        myTemp.add(myTemperature1);
        myTemp.add(myTemperature2);

        postrequestBody.setTemp(myTemp);
        return postrequestBody;
    }

    @Test
    public void putRequestPojo(){
        PostRequestBody postrequestBody = new PostRequestBody();
        postrequestBody.setEmail("faiyaz@gamil.com");
        postrequestBody.setFirst_name("faiyaz");
        postrequestBody.setLast_name("pasha");

        Response resp = given()
                .header("x-api-key","reqres_2bd774c43f544db78b99ff2a7f2fc01e")
                .header("content-Type","application/json")
                .body(postrequestBody)
                .when()
                .put("https://reqres.in/api/users/1");

        PostResponseBody postResponseBody = resp.as(PostResponseBody.class);
        Assert.assertEquals(resp.getStatusCode(),StatusCode.SUCCESS.code);
        System.out.println(postResponseBody.getFirst_name()+"-"+postResponseBody.getLast_name()+"-"+postResponseBody.getEmail());
    }

    @Test
    public void patchRequestPojo(){
        PostRequestBody postrequestBody = new PostRequestBody();
        postrequestBody.setEmail("faiyaz@gamil.com");
        postrequestBody.setFirst_name("faiyaz");

        Response resp = given()
                .header("x-api-key","reqres_2bd774c43f544db78b99ff2a7f2fc01e")
                .header("content-Type","application/json")
                .body(postrequestBody)
                .when()
                .patch("https://reqres.in/api/users/1");

        PostResponseBody postResponseBody = resp.as(PostResponseBody.class);
        Assert.assertEquals(resp.getStatusCode(),StatusCode.SUCCESS.code);
        System.out.println(postResponseBody.getFirst_name());
    }
}
