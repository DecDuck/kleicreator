package kleicreator.export.jobs;

import kleicreator.export.Exporter;
import kleicreator.export.interfaces.Job;
import kleicreator.logging.Logger;
import kleicreator.modloader.classes.ResourceAnimation;
import kleicreator.modloader.classes.ResourceTexture;
import kleicreator.modloader.resources.Resource;
import kleicreator.modloader.resources.ResourceManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

public class CopyResourcesJob implements Job {
    @Override
    public Map<String, String> run(Exporter exporter) throws Exception {
        Logger.Debug("Starting resource copy");
        for (Resource r : ResourceManager.resources) {
            if (r.Is(ResourceTexture.class)) {
                ResourceTexture m = r.Get();
                Files.copy(Paths.get(m.texPath), Paths.get(exporter.getOutput() + m.filePath + GetFileName(m.texPath)), StandardCopyOption.REPLACE_EXISTING);
                Files.copy(Paths.get(m.xmlPath), Paths.get(exporter.getOutput() + m.filePath + GetFileName(m.xmlPath)), StandardCopyOption.REPLACE_EXISTING);
            }
            if (r.Is(ResourceAnimation.class)) {
                ResourceAnimation m = r.Get();
                Files.copy(Paths.get(m.animFilePath), Paths.get(exporter.getOutput() + "/anim/" + GetFileName(m.animFilePath)), StandardCopyOption.REPLACE_EXISTING);
            }
        }
        Logger.Debug("Finished resource copy");
        return null;
    }

    @Override
    public String prettyName() {
        return "Copying Resources...";
    }

    private String GetFileName(String fname) {
        int pos = fname.lastIndexOf(File.separator);
        if (pos > -1)
            return fname.substring(pos + 1);
        else
            return fname;
    }
}
