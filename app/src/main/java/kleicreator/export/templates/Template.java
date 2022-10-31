package kleicreator.export.templates;


import kleicreator.export.AssetExporter;
import kleicreator.export.PrefabExporter;
import kleicreator.export.RecipeExporter;
import kleicreator.export.SpeechExporter;
import kleicreator.plugin.PluginBlob;
import kleicreator.plugin.PluginHandler;
import kleicreator.plugin.PluginMetadata;
import kleicreator.sdk.EventTrigger;
import kleicreator.sdk.item.Item;
import kleicreator.modloader.Mod;
import kleicreator.modloader.resources.Resource;
import kleicreator.modloader.resources.ResourceManager;
import kleicreator.sdk.item.ItemComponent;
import kleicreator.sdk.logging.Logger;

import java.util.List;

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
                ModmainCreate();
                break;
            case Modinfo:
                ModinfoCreate();
                break;
            case Item:
                ItemCreate();
                break;
        }
    }

    private void ModmainCreate(){
        String speechBlock = SpeechExporter.GenerateSpeech();
        ReplaceAll("$SPEECH$", speechBlock);

        ReplaceAll("$PREFABS$", PrefabExporter.GenerateModmainPrefabs());
        ReplaceAll("$RECIPES$", RecipeExporter.GenerateRecipeExport());

        String injects = "";

        for(PluginBlob blob : PluginHandler.blobs){
            injects += "-- Inject by ";
            injects += blob.core.Id();
            for(EventTrigger trigger : blob.triggers){
                injects += trigger.InjectModmainLines();
                injects += "\n\n\n"; // Sufficient padding
            }
        }

        ReplaceAll("$INJECT$", injects);
    }

    private void ModinfoCreate(){
        ReplaceAll("$MODNAME$", Mod.modName);
        ReplaceAll("$MODDESCRIPTION$", Mod.modDescription);
        ReplaceAll("$MODAUTHOR$", Mod.modAuthor);
        ReplaceAll("$MODVERSION$", Mod.modVersion);
        if (Mod.modIcon != -1) {
            // Resource modIcon = ResourceManager.resources.get(Mod.modIcon);
            ReplaceAll("$MODICON$", ""); // To-do
            ReplaceAll("$MODICONXML$", ""); //To-do
        } else {
            ReplaceAll("$MODICON$", "");
            ReplaceAll("$MODICONXML$", "");
        }
    }

    private void ItemCreate(){
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

        String filler = "";
        for(Item.Entry<Boolean, ItemComponent> pair : item.itemComponents){
            if(pair.a){
                List<String> lines = pair.b.ExportLines();
                for(String line : lines){
                    String nLine = "$INDENT$" + line + "\n";
                    filler += nLine;
                }
            }
        }
        ReplaceAll("$FILLER$", filler);
        ReplaceAll("$INDENT$", "    ");

        String injects = "";

        for(PluginBlob blob : PluginHandler.blobs){
            injects += "-- Inject by ";
            injects += blob.core.Id();
            for(EventTrigger trigger : blob.triggers){
                injects += trigger.InjectItemLines(item);
                injects += "\n\n\n"; // Sufficient padding
            }
        }

        ReplaceAll("$INJECT$", injects);
    }

    private void ReplaceAll(String a, String b) {
        for(int i = 0; i < template.length(); i++){
            template = template.replace(a, b);
        }
    }

    public String getTemplate() {
        return template;
    }

    public enum Type {
        Modmain,
        Modinfo,
        Item,
        Custom
    }
}
