package userManagement;

import core.StatusCode;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import pojo.ChatGptResponseBodyModels;
import pojo.Data;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ChatGptAutomate {

    public RequestSpecification requestSpecBuild(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://api.openai.com")
                .addHeader("Authorization","Bearer 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")
                .addPathParam("baseVersion","v1");

        RequestSpecification requestSpecification = requestSpecBuilder.build();  //reference is of interface(requestSpecification) but object is class(requestspecbuilder)
        return requestSpecification;
    }

    @Test
    public void getOpenAiModels(){
        Response resp = given()
                .spec(requestSpecBuild())
                .when()
                .get("/{baseVersion}/models");
        assertThat(resp.getStatusCode(), is(StatusCode.SUCCESS.code));
        ChatGptResponseBodyModels chatGptResponseBody = resp.as(ChatGptResponseBodyModels.class);
        System.out.println(chatGptResponseBody.getData().get(1).getOwned_by());
    }
    @Test
    public void getOpenAiModelSpecif(){
        Response resp = given()
                .spec(requestSpecBuild())
                .pathParam("model","gpt-realtime")
                .when()
                .get("/{baseVersion}/models/{model}");
        assertThat(resp.getStatusCode(), is(StatusCode.SUCCESS.code));
        Data data = resp.as(Data.class);
        System.out.println(data.getObject());


    }



}
