package constants;

import org.apache.commons.lang3.SystemUtils;

public class Constants {
    public static String FILE_LOCATION;

    public static void CreateConstants(){
        if(SystemUtils.IS_OS_WINDOWS){
            FILE_LOCATION = System.getenv("APPDATA") + "/dstguimodcreator";
            return;
        }
        FILE_LOCATION = System.getProperty("user.home") + "/.dstguimodcreator";
    }
}
