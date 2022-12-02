package kleicreator.data;

import kleicreator.util.ArgumentParser;

import java.io.File;

public class Constants {

    public static final String DATA_PATH = "/data/";
    public static final String PROJECT_PATH = "/projects/";
    public static final String EXPORT_PATH = "/exports/";
    public static final String CONFIG_PATH = "/config/";
    public static final String PLUGINS_PATH = "/plugins/";

    public static String KLEICREATOR_LOCATION;

    public static void CreateConstants() {
        if(ArgumentParser.doubleArguments.containsKey("--path")){
            KLEICREATOR_LOCATION = new File(ArgumentParser.doubleArguments.get("--path")).getAbsolutePath();
        }else{
            KLEICREATOR_LOCATION = System.getProperty("user.home") + "/.kleicreator";
        }
    }

    public static String GetDataDirectory(){
        return KLEICREATOR_LOCATION + DATA_PATH;
    }
    public static String GetProjectDirectory(){
        return KLEICREATOR_LOCATION + PROJECT_PATH;
    }
    public static String GetExportDirectory(){
        return KLEICREATOR_LOCATION + EXPORT_PATH;
    }
    public static String GetConfigDirectory(){
        return KLEICREATOR_LOCATION + CONFIG_PATH;
    }
    public static String GetPluginsDirectory(){
        return KLEICREATOR_LOCATION + PLUGINS_PATH;
    }


    public static String[] GetAllWorkingFolders(){
        return new String[]{
                KLEICREATOR_LOCATION + DATA_PATH,
                KLEICREATOR_LOCATION + PROJECT_PATH,
                KLEICREATOR_LOCATION + EXPORT_PATH,
                KLEICREATOR_LOCATION + CONFIG_PATH,
                KLEICREATOR_LOCATION + PLUGINS_PATH,
        };
    }
}
