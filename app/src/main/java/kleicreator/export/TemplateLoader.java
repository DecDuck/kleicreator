package kleicreator.export;

import kleicreator.export.templates.Template;
import kleicreator.sdk.logging.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class TemplateLoader {
    public static String ITEM_TEMPLATE;
    public static String MODMAIN_TEMPLATE;
    public static String MODINFO_TEMPLATE;

    public static void LoadTemplates() {
        try {
            ITEM_TEMPLATE = new Scanner(ClassLoader.getSystemClassLoader().getResource("templates/item.template").openStream(), "UTF-8").useDelimiter("\\A").next();
        } catch (IOException e) {
            Logger.Error(e);
        }
        try {
            MODMAIN_TEMPLATE = new Scanner(ClassLoader.getSystemClassLoader().getResource("templates/modmain.template").openStream(), "UTF-8").useDelimiter("\\A").next();
        } catch (IOException e) {
            Logger.Error(e);
        }
        try {
            MODINFO_TEMPLATE = new Scanner(ClassLoader.getSystemClassLoader().getResource("templates/modinfo.template").openStream(), "UTF-8").useDelimiter("\\A").next();
        } catch (IOException e) {
            Logger.Error(e);
        }
    }

}
