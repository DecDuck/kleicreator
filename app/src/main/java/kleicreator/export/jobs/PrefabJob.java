package kleicreator.export.jobs;

import kleicreator.export.Exporter;
import kleicreator.export.interfaces.Job;
import kleicreator.items.Item;
import kleicreator.modloader.Mod;

import java.util.HashMap;
import java.util.Map;

public class PrefabJob implements Job {
    public static final String DEFAULT_TEMPLATE = "    \"$NAME$\",\n";
    @Override
    public Map<String, String> run(Exporter exporter) throws Exception {
        StringBuilder output = new StringBuilder();
        for (Item i : Mod.items) {
            output.append(DEFAULT_TEMPLATE.replace("$NAME$", i.itemId));
        }
        HashMap<String, String> export = new HashMap<>();
        export.put("prefab", output.toString());
        return export;
    }

    @Override
    public String prettyName() {
        return "Prefab Job";
    }
}
