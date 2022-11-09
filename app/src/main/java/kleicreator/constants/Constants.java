package kleicreator.constants;

import kleicreator.master.ArgumentParser;

import java.io.File;

public class Constants {

    public static Constants constants;

    public String KLEICREATOR_LOCATION;

    public void CreateConstants() {
        if(ArgumentParser.doubleArguments.containsKey("--path")){
            KLEICREATOR_LOCATION = new File(ArgumentParser.doubleArguments.get("--path")).getAbsolutePath();
        }else{
            KLEICREATOR_LOCATION = System.getProperty("user.home") + "/.kleicreator";
        }
    }

    public String FetchLogLocation(){
        return KLEICREATOR_LOCATION + "/log.txt";
    }

    public String FetchExportLocation(String escapedModName){
        return KLEICREATOR_LOCATION + "/exported/" + escapedModName + "/";
    }

    public String FetchModLocation(){
        return KLEICREATOR_LOCATION + "/mods/";
    }
}
