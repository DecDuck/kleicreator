package constants;

import org.apache.commons.lang3.SystemUtils;

public class Constants {
    public static String FILE_LOCATION;

    public static void CreateConstants(){

        FILE_LOCATION = System.getProperty("user.home") + "/.deepcore/kleicreator";
    }
}
