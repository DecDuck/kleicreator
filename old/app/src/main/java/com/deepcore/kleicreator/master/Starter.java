package com.deepcore.kleicreator.master;

import com.thoughtworks.xstream.converters.ConversionException;
import com.deepcore.kleicreator.constants.Constants;
import com.deepcore.kleicreator.sdk.logging.Logger;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

public class Starter {
    public static int startCounter = 1;

    public static void main(String[] args) {
        try {
            while (startCounter > 0) {
                startCounter--;
                Master.Main(args);
            }
            System.exit(0);
        } catch (Throwable e) {
            Logger.Log("Oh no! We got an error!");
            if (e instanceof ConversionException) {
                Logger.Log("Attempting to handle the error...");
                try {
                    Files.delete(Path.of(Constants.FILE_LOCATION + "/config.xml"));
                    Master.Main(args);
                    main(args);
                } catch (IOException ex) {

                }
            }
            Logger.Log("More oh-nos, we got an error and couldn't handle it!");
            Logger.Error(ExceptionUtils.getStackTrace(e));
            try {
                Files.writeString(Path.of(Constants.FILE_LOCATION + "_crash_" + new Date().getTime() + ".log"), ExceptionUtils.getStackTrace(e));
            } catch (IOException ex) {
                Logger.Error("We are seriously screwed guys, we can't write to a crash file. We're just gonna give up.");
                return;
            }
            JOptionPane.showMessageDialog(new JFrame(), "Hey! We hit an error that we couldn't handle. We wrote the error message to ~/.deepcore/, check it out and possibly file a bug report.", "ERROR", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
