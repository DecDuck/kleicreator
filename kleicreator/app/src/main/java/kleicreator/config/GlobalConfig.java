package kleicreator.config;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import kleicreator.sdk.logging.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static kleicreator.sdk.constants.Constants.FILE_LOCATION;

public class GlobalConfig {

    public static Theme theme = Theme.Light;
    public static boolean askSaveOnLeave = true;
    public static XStream stream;
    public static Map<String, Object> values = new HashMap<>();

    public static void CreateStream() {
        stream = new XStream(new DomDriver());
        stream.alias("kleicreator/config", Config.class);

        if (!new File(FILE_LOCATION + "/config.xml").exists()) {
            new Config().Save();
            Logger.Debug("Created config file with default settings");
        }
    }

    public enum Theme {
        Default,
        Light,
        Dark
    }

}
