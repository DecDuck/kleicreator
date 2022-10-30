package com.deepcore.kleicreator.export;

import com.deepcore.kleicreator.export.templates.Template;
import com.deepcore.kleicreator.logging.Logger;
import com.deepcore.kleicreator.modloader.Mod;

import java.util.ArrayList;
import java.util.List;

public class Templates {
    public static Template modmain;
    public static Template modinfo;
    public static List<Template> itemTemplates = new ArrayList<Template>();

    public static void CreateTemplates() {
        modmain = new Template(TemplateLoader.MODMAIN_TEMPLATE, Template.Type.Modmain);
        modinfo = new Template(TemplateLoader.MODINFO_TEMPLATE, Template.Type.Modinfo);
        for (int i = 0; i < Mod.items.size(); i++) {
            itemTemplates.add(
                    new Template(TemplateLoader.ITEM_TEMPLATE, Template.Type.Item, Mod.items.get(i))
            );
        }
        Logger.Log("Instantiated 'Template' object for export.");
    }
}
