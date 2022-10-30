package com.deepcore.kleicreator.export.templates;

import com.deepcore.kleicreator.export.AssetExporter;
import com.deepcore.kleicreator.export.PrefabExporter;
import com.deepcore.kleicreator.export.RecipeExporter;
import com.deepcore.kleicreator.export.SpeechExporter;
import com.deepcore.kleicreator.items.Item;
import com.deepcore.kleicreator.logging.Logger;
import com.deepcore.kleicreator.modloader.Mod;
import com.deepcore.kleicreator.modloader.resources.Resource;
import com.deepcore.kleicreator.modloader.resources.ResourceManager;

public class Template {

    private String template;
    private final Type templateType;
    private Item item;
    public Template(String rawTemplate, Type t) {
        this.template = rawTemplate;
        this.templateType = t;
    }

    public Template(String rawTemplate, Type t, Item item) {
        this.template = rawTemplate;
        this.templateType = t;
        this.item = item;
    }

    public void Create() {
        switch (templateType) {
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
                if (Mod.modIcon != -1) {
                    Resource modIcon = ResourceManager.resources.get(Mod.modIcon);
                    ReplaceAll("$MODICON$", ""); // To-do
                    ReplaceAll("$MODICONXML$", ""); //To-do
                } else {
                    ReplaceAll("$MODICON$", "");
                    ReplaceAll("$MODICONXML$", "");
                }
                break;
            case Item:
                ReplaceAll("$ID$", item.itemId);
                ReplaceAll("$UPPERID$", item.itemId.toUpperCase());
                ReplaceAll("$NAME$", item.itemName);
                ReplaceAll("$STACKSIZE$", String.valueOf(item.stackSize));

                Logger.Debug("Item texture is " + item.itemTexture);
                if (item.itemTexture == -1) {
                    ReplaceAll("$ASSETS$", "");
                } else {
                    ReplaceAll("$ASSETS$", AssetExporter.ExportAsset(ResourceManager.inventoryimages.get(item.itemTexture).Get()));
                }
                //TODO $UPPER$
                //TODO $FILLER$
                break;
        }
    }

    private void ReplaceAll(String a, String b) {
        template = template.replace(a, b);
    }

    public String getTemplate() {
        return template;
    }

    public enum Type {
        Modmain,
        Modinfo,
        Item
    }
}
