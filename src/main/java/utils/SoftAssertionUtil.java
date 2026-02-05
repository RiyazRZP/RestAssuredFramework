package utils;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class SoftAssertionUtil {
    private final SoftAssert softAssert;

    public SoftAssertionUtil(){
        softAssert = new SoftAssert();
    }

    public void assertEqual(Object a, Object b, String message){
        try{
            softAssert.assertEquals(a,b,message);
        }catch (AssertionError e){
            softAssert.fail(message);
        }

        //or just use this it works fine

        //softAssert.assertEquals(a,b,message);
    }

    public void assertTrue(boolean condition, String message){
        try{
            softAssert.assertTrue(condition,message);
        }catch(AssertionError e){
            softAssert.fail(message);
        }
    }
    public void assertAll(){
        softAssert.assertAll();
    }

}
