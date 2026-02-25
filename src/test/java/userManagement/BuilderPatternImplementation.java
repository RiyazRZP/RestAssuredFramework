package userManagement;

import core.StatusCode;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BuilderPatternImplementation {

    private RequestSpecification requestSpecBuilder(){
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addHeader("x-api-key", "reqres_2bd774c43f544db78b99ff2a7f2fc01e")
                .setContentType("application/json")
                .build();
        return requestSpecification;
    }

    private ResponseSpecification responseSpecBuilder(int statusCode){
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectContentType("application/json");
        responseSpecBuilder.expectStatusCode(statusCode);
        ResponseSpecification responseSpecification = responseSpecBuilder.build();
        return responseSpecification;
    }


    @Test
    public void builderPatternImp() {
        Response resp = given()
                .spec(requestSpecBuilder())
                .when()
                .get("https://reqres.in/api/users");

        resp.then().assertThat().statusCode(200);

    }

    @Test
    public void builderPatternImp2(){
        Response resp = given()
                .spec(requestSpecBuilder())
                .pathParam("id",1)
                .when()
                .get("https://reqres.in/api/users/{id}");

        resp.then().spec(responseSpecBuilder(200));
    }
}
