package com.deepcore.kleicreator.constants;

import com.deepcore.kleicreator.master.ArgumentParser;

import java.io.File;
import java.nio.file.Path;

public class Constants {
    public static String FILE_LOCATION;

    public static void CreateConstants() {
        if(ArgumentParser.doubleArguments.containsKey("--path")){
            FILE_LOCATION = new File(ArgumentParser.doubleArguments.get("--path")).getAbsolutePath();
        }else{
            FILE_LOCATION = System.getProperty("user.home") + "/.deepcore/kleicreator";
        }
    }
}
