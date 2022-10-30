package kleicreator.sdk.constants;

import kleicreator.sdk.ArgumentParser;

import java.io.File;

public class Constants {
    public static String KLEICREATOR_LOCATION;

    public static void CreateConstants() {
        if(ArgumentParser.doubleArguments.containsKey("--path")){
            KLEICREATOR_LOCATION = new File(ArgumentParser.doubleArguments.get("--path")).getAbsolutePath();
        }else{
            KLEICREATOR_LOCATION = System.getProperty("user.home") + "/.deepcore/kleicreator";
        }
    }
}
