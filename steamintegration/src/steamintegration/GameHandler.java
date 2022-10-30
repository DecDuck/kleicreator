package steamintegration;

import com.deepcore.kleicreator.sdk.gui.ModEditor;

import java.io.File;
import java.nio.file.Path;

public class GameHandler {
    public static boolean GameIntegration = false;
    public static String GameFolder;
    public static String ModsFolder;

    public static Path GetDefaultSteamPath(){
        String os = System.getProperty("os.name");
        if(os.toLowerCase().contains("win")){
            return Path.of("C:\\Program Files\\Steam\\");
        }else if(os.toLowerCase().contains("mac")){
            return Path.of(System.getProperty("user.home") + "/Library/Application Support/Steam/");
        }else {
            return Path.of(System.getProperty("user.home") + "/.steam/steam/");
        }
    }

    public static void CheckSteamIntegration(){
        File gameFolder = Path.of(Plugin.steamPath + "/steamapps/common/Don't Starve Together/").toFile();
        if(gameFolder.exists()){
            GameIntegration = true;
            GameFolder = gameFolder.getAbsolutePath();
            ModsFolder = GameFolder + "/mods";
        }else{
            ModEditor.ShowWarning("Cannot load Steam Integration", "Cannot find steam folder, not loading Steam Integration");
        }
    }
}
