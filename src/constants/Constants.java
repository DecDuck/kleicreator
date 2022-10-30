package constants;

public class Constants {
    public static String FILE_LOCATION;

    public static void CreateConstants() {

        FILE_LOCATION = System.getProperty("user.home") + "/.deepcore/kleicreator";
    }
}
