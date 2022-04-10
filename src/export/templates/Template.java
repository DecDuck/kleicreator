package export.templates;

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
