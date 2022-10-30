package com.deepcore.kleicreator.config;

import com.deepcore.kleicreator.logging.Logger;
import com.deepcore.kleicreator.plugin.PluginHandler;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.deepcore.kleicreator.constants.Constants.FILE_LOCATION;

public class Config {

    private Map<String, Object> values = new HashMap<>();


    public void Save(String name, Object data){
        GlobalConfig.values.put(name, data);
    }

    public Object Load(String name){
        return GlobalConfig.values.get(name);
    }

    public void Save() {
        Save("theme", GlobalConfig.theme);
        Save("askSaveOnLeave", GlobalConfig.askSaveOnLeave);

        values = GlobalConfig.values;

        try {
            File file = new File(FILE_LOCATION + "/config.xml");
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fr = new FileWriter(file, false);
            fr.write(GlobalConfig.stream.toXML(this));
            fr.close();
        } catch (IOException e) {
            Logger.Error(ExceptionUtils.getStackTrace(e));
        }
    }

    public void Load() {
        File file = new File(FILE_LOCATION + "/config.xml");
        if(!file.exists()){
            return;
        }

        Config config = (Config) GlobalConfig.stream.fromXML(file);

        GlobalConfig.values = config.values;

        GlobalConfig.theme = (GlobalConfig.Theme) Load("theme");
        GlobalConfig.askSaveOnLeave = (boolean) Load("askSaveOnLeave");
    }
}
