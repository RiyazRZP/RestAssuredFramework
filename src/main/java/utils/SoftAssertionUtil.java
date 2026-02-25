package utils;

import org.testng.asserts.SoftAssert;

public class SoftAssertionUtil {

    private static SoftAssert softAssert;

    private SoftAssertionUtil() {};

    public static SoftAssert getInstance(){
        if(softAssert == null){
            softAssert = new SoftAssert();
        }
        return softAssert;
    }

    public static void assertEqual(Object a, Object b, String message){
        try{
            getInstance().assertEquals(a,b,message);
        }catch (AssertionError e){
            getInstance().fail(message);
        }

        //or just use this it works fine

        //softAssert.assertEquals(a,b,message);
    }

    public static void assertTrue(boolean condition, String message){
        try{
            getInstance().assertTrue(condition,message);
        }catch(AssertionError e){
            getInstance().fail(message);
        }
    }
    public static  void assertAll(){
        getInstance().assertAll();
    }

//    private final SoftAssert softAssert;
//
//    public SoftAssertionUtil(){
//        softAssert = new SoftAssert();
//    }
//
//    public void assertEqual(Object a, Object b, String message){
//        try{
//            softAssert.assertEquals(a,b,message);
//        }catch (AssertionError e){
//            softAssert.fail(message);
//        }
//
//        //or just use this it works fine
//
//        //softAssert.assertEquals(a,b,message);
//    }
//
//    public void assertTrue(boolean condition, String message){
//        try{
//            softAssert.assertTrue(condition,message);
//        }catch(AssertionError e){
//            softAssert.fail(message);
//        }
//    }
//    public void assertAll(){
//        softAssert.assertAll();
//    }

}
