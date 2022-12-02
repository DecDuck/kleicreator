package kleicreator.export.jobs;

import kleicreator.export.Exporter;
import kleicreator.export.interfaces.Job;
import kleicreator.items.Item;
import kleicreator.items.ItemComponent;
import kleicreator.util.Logger;
import kleicreator.resources.types.ResourceTexture;
import kleicreator.resources.ResourceManager;

import java.util.List;
import java.util.Map;

public class ItemJob implements Job {
    private final Item item;

    public ItemJob(Item item) {
        this.item = item;
    }

    @Override
    public Map<String, String> run(Exporter exporter) throws Exception {
        String template = this.loadTemplate("item.template");

        template = sub(template, "$ID$", item.itemId);
        template = sub(template, "$UPPERID$", item.itemId.toUpperCase());
        template = sub(template, "$NAME$", item.itemName);
        template = sub(template, "$STACKSIZE$", String.valueOf(item.stackSize));

        Logger.Debug("Item texture is " + item.itemTexture);
        if (item.itemTexture == -1) {
            template = sub(template, "$ASSETS$", "");
        } else {
            ResourceTexture texture = ResourceManager.inventoryimages.get(item.itemTexture).Get();
            AssetJob assetJob = new AssetJob(texture);
            exporter.ExecuteJob(assetJob);
            template = sub(template, "$ASSETS$", exporter.getShared().get("resource_"+texture.filePath));
        }
        //TODO $UPPER$

        StringBuilder filler = new StringBuilder();
        for(Item.Entry<Boolean, ItemComponent> pair : item.itemComponents){
            if(pair.a){
                List<String> lines = pair.b.ExportLines();
                for(String line : lines){
                    String nLine = "$INDENT$" + line + "\n";
                    filler.append(nLine);
                }
            }
        }
        template = sub(template, "$FILLER$", filler.toString());
        template = sub(template, "$INDENT$", "    ");
        exporter.Write("scripts/prefabs/"+item.itemId+".lua", template);
        return null;
    }

    @Override
    public String prettyName() {
        return "Item Job ("+item.itemId+")";
    }


}
