package userManagement;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CoopApi {

    public RequestSpecification requestSpecification(){
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addHeader("Authorization","Bearer "+postAuthenticationForToken())
                .build();

        return requestSpecification;
    }


    public String postAuthenticationForToken(){
        Response resp = given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .when()
                .formParam("client_id","lead Tomorrow Future")
                .formParam("client_secret","90e455e2868aaf76534bcc6945201838")
                .formParam("grant_type","client_credentials")
                .post("http://coop.apps.symfonycasts.com/token");

        assertThat(resp.getBody().asString(), containsString("access_token"));
        String token = resp.path("access_token");
        return token;
    }

    @Test
    public void postRequestBarnUnlock(){
        Response resp = given()
                .spec(requestSpecification())
                .when()
                .post("http://coop.apps.symfonycasts.com/api/5215/barn-unlock");
        assertThat(resp.getBody().jsonPath().get("success"), Matchers.is(true));
        System.out.println(resp.getBody().asString());
    }
    @Test
    public void postRequestFeedChicken(){
        Response resp = given()
                .spec(requestSpecification())
                .when()
                .post("http://coop.apps.symfonycasts.com/api/5215/chickens-feed");
        assertThat(resp.getBody().jsonPath().get("success"), Matchers.is(true));
        System.out.println(resp.getBody().asString());
    }

    @Test
    public void postRequestEggCollectForDay(){
        Response resp = given()
                .spec(requestSpecification())
                .when()
                .post("http://coop.apps.symfonycasts.com/api/5215/eggs-count");
        assertThat(resp.getBody().jsonPath().get("success"), Matchers.is(true));
        System.out.println(resp.getBody().asString());
    }

}
