package kleicreator.export.jobs;

import kleicreator.export.Exporter;
import kleicreator.export.interfaces.Job;
import kleicreator.modloader.Mod;

import java.util.Map;

public class ModinfoJob implements Job {
    @Override
    public Map<String, String> run(Exporter exporter) throws Exception {
        String template = this.loadTemplate("modinfo.template");

        template = sub(template, "$MODNAME$", Mod.modName);
        template = sub(template, "$MODDESCRIPTION$", Mod.modDescription);
        template = sub(template, "$MODAUTHOR$", Mod.modAuthor);
        template = sub(template, "$MODVERSION$", Mod.modVersion.toString());
        if (Mod.modIcon != -1) {
            // Resource modIcon = ResourceManager.resources.get(Mod.modIcon);
            template = sub(template, "$MODICON$", ""); // To-do
            template = sub(template, "$MODICONXML$", ""); //To-do
        } else {
            template = sub(template, "$MODICON$", "");
            template = sub(template, "$MODICONXML$", "");
        }

        exporter.Write("modinfo.lua", template);
        return null;
    }

    @Override
    public String prettyName() {
        return "Modinfo Job";
    }
}
