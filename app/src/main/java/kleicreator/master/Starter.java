package kleicreator.master;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.StreamException;
import kleicreator.sdk.constants.Constants;
import kleicreator.sdk.logging.Logger;

import javax.swing.*;
import java.io.File;
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
                /*
                * This is here because I had an issue with config messing up loading
                * */
                boolean success = new File(Constants.constants.KLEICREATOR_LOCATION + "/config")
                        .renameTo(
                                new File(Constants.constants.KLEICREATOR_LOCATION
                                        + "/config_bak_"
                                        + new Date().getTime()
                                )
                        );
                if(success){
                    Master.Main(args);
                    main(args);
                    return;
                }
            }
            if (e instanceof StreamException) {
                new File(Constants.constants.KLEICREATOR_LOCATION).mkdirs();
                Master.Main(args);
                main(args);
                return;
            }
            Logger.Log("More oh-nos, we got an error and couldn't handle it!");
            Logger.Error(e);
            String crashDumpFilePath = Constants.constants.KLEICREATOR_LOCATION + "_crash_" + new Date().getTime() + ".log";
            try {
                Files.writeString(Path.of(crashDumpFilePath), Logger.currentLog);
            } catch (IOException ex) {
                Logger.Error("We are seriously screwed guys, we can't write to a crash file. We're just gonna give up.");
                return;
            }
            JOptionPane.showMessageDialog(new JFrame(), "Hey! We hit an error that we couldn't handle. We wrote the error message to "+crashDumpFilePath+", check it out and possibly file a bug report.", "ERROR", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
