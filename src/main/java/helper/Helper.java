package helper;

import java.io.File;
import java.util.Date;

public class Helper {


    public static String Timestamp() {
        Date now = new Date();
        String timestamp = now.toString().replace(":", "/");
        return timestamp;
    }


    public static void CreateFolder(String path) {
        //File is a class inside java.io package
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();//mkdir is used to create folder
        } else
            System.out.println("Folder already created");
    }
}
