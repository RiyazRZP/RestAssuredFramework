package userManagement;

import core.BaseTest;
import core.StatusCode;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojo.BookStoreTokenUserReqBody;
import pojo.CollectionOfIsbns;
import pojo.ReqBodyAddBook;
import utils.DataProviderUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ImpOfApiChaning {

    int count =0;

    public String userGenerationApiPost(BookStoreTokenUserReqBody reqBody){
        Response resp = given()
                .contentType("application/json")
                .body(reqBody)
                .when()
                .post("https://bookstore.toolsqa.com/Account/v1/User");

        String userId = resp.path("userID");
        return userId;
    }

    public String generateTokenApiPost(BookStoreTokenUserReqBody reqBody){
        Response resp = given()
                .contentType("application/json")
                .body(reqBody)
                .when()
                .post("https://bookstore.toolsqa.com/Account/v1/GenerateToken");

        String token = resp.path("token");
        System.out.println(token);
        return token;
    }
    @Test(dataProvider = "dpJson", dataProviderClass = DataProviderUtil.class)
    public void postAddBook(Map<String,String> result){
        BookStoreTokenUserReqBody bookStoreTokenUserReqBody = new BookStoreTokenUserReqBody();
        bookStoreTokenUserReqBody.setUserName(result.get("userName"));
        bookStoreTokenUserReqBody.setPassword(result.get("password"));


        String user="";
        if(count < 1){
            user = userGenerationApiPost(bookStoreTokenUserReqBody);
        }
        System.out.println(user);
        String token = generateTokenApiPost(bookStoreTokenUserReqBody);

        CollectionOfIsbns collectionOfIsbnsClass = new CollectionOfIsbns();
        collectionOfIsbnsClass.setIsbn(result.get("isbn"));

        List<CollectionOfIsbns> collectionOfIsbns1Field = new ArrayList<>();
        collectionOfIsbns1Field.add(collectionOfIsbnsClass);

        ReqBodyAddBook reqBodyAddBook = new ReqBodyAddBook();
        reqBodyAddBook.setUserId(user);
        reqBodyAddBook.setCollectionOfIsbns(collectionOfIsbns1Field);

        Response resp  = given()
                .contentType("application/json")
                .header("Authorization","Bearer "+token)
                .body(reqBodyAddBook)
                .when()
                .post("https://bookstore.toolsqa.com/BookStore/v1/Books");

        resp.then().statusCode(StatusCode.CREATED.code);
        System.out.println(resp.getBody().asString());
    }
}
