package export.templates;

import export.AssetExporter;
import export.PrefabExporter;
import export.RecipeExporter;
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

                ReplaceAll("$PREFABS$", PrefabExporter.GenerateModmainPrefabs());
                ReplaceAll("$RECIPES$", RecipeExporter.GenerateRecipeExport());
                break;
            case Modinfo:
                ReplaceAll("$MODNAME$", Mod.modName);
                ReplaceAll("$MODDESCRIPTION$", Mod.modDescription);
                ReplaceAll("$MODAUTHOR$", Mod.modAuthor);
                ReplaceAll("$MODVERSION$", Mod.modVersion);
                if(Mod.modIcon != -1){
                    Resource modIcon = ResourceManager.resources.get(Mod.modIcon);
                    ReplaceAll("$MODICON$", modIcon.texture.texPath);
                    ReplaceAll("$MODICONXML$", modIcon.texture.xmlPath);
                }else{
                    ReplaceAll("$MODICON$", "");
                    ReplaceAll("$MODICONXML$", "");
                }
                break;
            case Item:
                ReplaceAll("$ID$", item.itemId);
                ReplaceAll("$UPPERID$", item.itemId.toUpperCase());
                ReplaceAll("$NAME$", item.itemName);
                ReplaceAll("$STACKSIZE$", String.valueOf(item.stackSize));

                Logger.Log("Item texture is " + item.itemTexture);
                if(item.itemTexture == -1){
                    ReplaceAll("$ASSETS$", "");
                }else{
                    ReplaceAll("$ASSETS$", AssetExporter.ExportAsset(ResourceManager.inventoryimages.get(item.itemTexture)));
                }
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
