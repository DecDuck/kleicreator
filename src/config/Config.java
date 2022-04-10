package config;

import logging.Logger;

import java.io.*;

import static constants.Constants.FILE_LOCATION;

public class Config {

    public boolean darkMode;
    public boolean askSaveOnLeave;
    public String modsLocation;

    public void Save(){
        darkMode = GlobalConfig.darkMode;
        askSaveOnLeave = GlobalConfig.askSaveOnLeave;
        modsLocation = GlobalConfig.modsLocation;

        try {
            File file = new File(FILE_LOCATION + "/config.xml");
            FileWriter fr = new FileWriter(file, false);
            fr.write(GlobalConfig.stream.toXML(this));
            fr.close();
        } catch (IOException e) {
            Logger.Error(e.getLocalizedMessage());
        }
    }

    public void Load(){
        try {
            File file = new File(FILE_LOCATION + "/config.xml");
            FileInputStream  fr = new FileInputStream (file);
            byte[] data = new byte[(int) file.length()];
            fr.read(data);
            fr.close();

            String xml = new String(data, "UTF-8");

            Config config = (Config) GlobalConfig.stream.fromXML(xml);

            GlobalConfig.darkMode = config.darkMode;
            GlobalConfig.askSaveOnLeave = config.askSaveOnLeave;
            GlobalConfig.modsLocation = config.modsLocation;
        } catch (FileNotFoundException e) {
            Logger.Error(e.getLocalizedMessage());
        } catch (IOException e) {
            Logger.Error(e.getLocalizedMessage());
        }

    }
}
