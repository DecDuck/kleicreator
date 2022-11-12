package kleicreator.export.jobs;

import kleicreator.export.Exporter;
import kleicreator.export.interfaces.Job;
import kleicreator.logging.Logger;
import kleicreator.modloader.resources.ResourceManager;

import java.io.File;
import java.util.Map;

public class SetupJob implements Job {
    @Override
    public Map<String, String> run(Exporter exporter) throws Exception {
        new File(exporter.getOutput()).mkdirs();
        new File(exporter.getOutput() + "images/inventoryimages").mkdirs();
        new File(exporter.getOutput() + "images/bigportraits").mkdir();
        new File(exporter.getOutput() + "scripts/prefabs").mkdirs();
        new File(exporter.getOutput() + "anim").mkdir();
        ResourceManager.GenerateResourceLists();
        return null;
    }

    @Override
    public String prettyName() {
        return "Setting up...";
    }
}
