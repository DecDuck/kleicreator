package export.templates;

import export.SpeechExporter;
import logging.Logger;
import modloader.Mod;
import modloader.ModLoader;
import items.Item;
import modloader.resources.Resource;
import modloader.resources.ResourceManager;
import speech.SpeechFile;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

public class Template {

    public enum Type{
        Modmain,
        Modinfo,
        Item
    }

    private String template;
    private Type templateType;
    private Item item;

    public Template(String rawTemplate, Type t){
        this.template = rawTemplate;
        this.templateType = t;
    }
    public Template(String rawTemplate, Type t, Item item){
        this.template = rawTemplate;
        this.templateType = t;
        this.item = item;
    }

    public void Create(){
        switch(templateType){
            case Modmain:
                String speechBlock = SpeechExporter.GenerateSpeech();
                ReplaceAll("$SPEECH$", speechBlock);

                //TODO $PREFABS$
                break;
            case Modinfo:
                ReplaceAll("$MODNAME$", Mod.modName);
                ReplaceAll("$MODDESCRIPTION$", Mod.modDescription);
                ReplaceAll("$MODAUTHOR$", Mod.modAuthor);
                ReplaceAll("$MODVERSION$", Mod.modVersion);
                Resource modIcon = ResourceManager.resources.get(Mod.modIcon);
                ReplaceAll("$MODICON$", modIcon.texture.texPath);
                ReplaceAll("$MODICONXML$", modIcon.texture.xmlPath);
                break;
            case Item:
                ReplaceAll("$ID$", item.itemId);
                ReplaceAll("$UPPERID$", item.itemId.toUpperCase());
                ReplaceAll("$NAME$", item.itemName);

                //TODO $ASSETS$
                //TODO $UPPER$
                //TODO $FILLER$
                break;
        }
    }

    private void ReplaceAll(String a, String b){
        template = template.replace(a, b);
    }

    public String getTemplate() {
        return template;
    }
}
