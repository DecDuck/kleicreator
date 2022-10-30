package com.deepcore.kleicreator.export;

import com.deepcore.kleicreator.export.templates.Template;
import com.deepcore.kleicreator.sdk.logging.Logger;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class TemplateLoader {

    public static final URL ITEM_TEMPLATE_LOCATION = Template.class.getResource("item.template");
    public static final URL MODMAIN_TEMPLATE_LOCATION = Template.class.getResource("modmain.template");
    public static final URL MODINFO_TEMPLATE_LOCATION = Template.class.getResource("modinfo.template");
    public static String ITEM_TEMPLATE;
    public static String MODMAIN_TEMPLATE;
    public static String MODINFO_TEMPLATE;

    public static void LoadTemplates() {
        try {
            ITEM_TEMPLATE = LoadTemplate(ITEM_TEMPLATE_LOCATION);
        } catch (IOException e) {
            Logger.Error(ExceptionUtils.getStackTrace(e));
        }
        try {
            MODMAIN_TEMPLATE = LoadTemplate(MODMAIN_TEMPLATE_LOCATION);
        } catch (IOException e) {
            Logger.Error(ExceptionUtils.getStackTrace(e));
        }
        try {
            MODINFO_TEMPLATE = LoadTemplate(MODINFO_TEMPLATE_LOCATION);
        } catch (IOException e) {
            Logger.Error(ExceptionUtils.getStackTrace(e));
        }
    }

    private static String LoadTemplate(URL location) throws IOException {
        Scanner s = new Scanner(location.openStream()).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        Logger.Debug("Loaded template: " + location);
        return result;
    }

}
