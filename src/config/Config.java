package config;

import logging.Logger;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static constants.Constants.FILE_LOCATION;

public class Config {

    public GlobalConfig.Theme theme;
    public boolean askSaveOnLeave;
    public String modsLocation;

    public void Save() {
        theme = GlobalConfig.theme;
        askSaveOnLeave = GlobalConfig.askSaveOnLeave;
        modsLocation = GlobalConfig.modsLocation;

        try {
            File file = new File(FILE_LOCATION + "/config.xml");
            FileWriter fr = new FileWriter(file, false);
            fr.write(GlobalConfig.stream.toXML(this));
            fr.close();
        } catch (IOException e) {
            Logger.Error(ExceptionUtils.getStackTrace(e));
        }
    }

    public void Load() {
        try {
            File file = new File(FILE_LOCATION + "/config.xml");
            FileInputStream fr = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fr.read(data);
            fr.close();

            String xml = new String(data, StandardCharsets.UTF_8);

            Config config = (Config) GlobalConfig.stream.fromXML(xml);

            GlobalConfig.theme = config.theme;
            GlobalConfig.askSaveOnLeave = config.askSaveOnLeave;
            GlobalConfig.modsLocation = config.modsLocation;
        } catch (FileNotFoundException e) {
            Logger.Error(ExceptionUtils.getStackTrace(e));
        } catch (IOException e) {
            Logger.Error(ExceptionUtils.getStackTrace(e));
        }

    }
}
