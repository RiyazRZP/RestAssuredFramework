package userManagement;

import core.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class AuthApisTest extends BaseTest {
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
}
