package com.deepcore.kleicreator.export;

import com.deepcore.kleicreator.items.Item;
import com.deepcore.kleicreator.modloader.Mod;

public class PrefabExporter {

    public static final String DEFAULT_TEMPLATE = "    \"$NAME$\",\n";

    public static String GenerateModmainPrefabs() {
        String output = "";
        for (Item i : Mod.items) {
            output += DEFAULT_TEMPLATE.replace("$NAME$", i.itemId);
        }
        return output;
    }
}
