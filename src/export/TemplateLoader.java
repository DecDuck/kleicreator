package export;

import export.templates.Template;
import logging.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class TemplateLoader {

    public static String ITEM_TEMPLATE;
    public static String MODMAIN_TEMPLATE;
    public static String MODINFO_TEMPLATE;

    public static final String ITEM_TEMPLATE_LOCATION = Template.class.getResource("item.template").getPath();
    public static final String MODMAIN_TEMPLATE_LOCATION = Template.class.getResource("modmain.template").getPath();
    public static final String MODINFO_TEMPLATE_LOCATION = Template.class.getResource("modinfo.template").getPath();

    public static void LoadTemplates(){
        ITEM_TEMPLATE = LoadTemplate(ITEM_TEMPLATE_LOCATION);
        MODMAIN_TEMPLATE = LoadTemplate(MODMAIN_TEMPLATE_LOCATION);
        MODINFO_TEMPLATE = LoadTemplate(MODINFO_TEMPLATE_LOCATION);
    }

    private static String LoadTemplate(String location){
        File f = new File(location);
        String returnValue = "";
        try {
            Scanner reader = new Scanner(f);
            while(reader.hasNextLine()){
                String data = reader.nextLine() + "\n";
                returnValue = returnValue + data;
            }
        } catch (FileNotFoundException e) {
            Logger.Error(e.getLocalizedMessage());
        }
        Logger.Log("Loaded template: " + location);
        return returnValue;
    }

}
