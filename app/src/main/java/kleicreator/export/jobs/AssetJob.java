package kleicreator.export.jobs;

import kleicreator.export.Exporter;
import kleicreator.export.interfaces.Job;
import kleicreator.modloader.classes.ResourceTexture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetJob implements Job {
    private final ResourceTexture resource;
    public AssetJob(ResourceTexture resource){
        this.resource = resource;
    }
    @Override
    public Map<String, String> run(Exporter exporter) throws Exception {
        HashMap<String, String> export = new HashMap<>();
        export.put("resource_" + resource.filePath, String.format("    Asset(\"%s\", \"%s\"),\n    Asset(\"%s\", \"%s\"),\n",
                "IMAGE",
                resource.filePath + GetFileName(resource.texPath),
                "ATLAS",
                resource.filePath + GetFileName(resource.xmlPath)));
        return export;
    }

    @Override
    public String prettyName() {
        return "Asset Job ("+resource.filePath+")";
    }

    private String GetFileName(String fname) {
        int pos = fname.lastIndexOf(File.separator);
        if (pos > -1)
            return fname.substring(pos + 1);
        else
            return fname;
    }
}
