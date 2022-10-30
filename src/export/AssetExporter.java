package export;

import modloader.ModLoader;
import modloader.classes.ResourceTexture;

public class AssetExporter {
    public static String ExportAsset(ResourceTexture r) {
        return String.format("    Asset(\"%s\", \"%s\"),\n    Asset(\"%s\", \"%s\"),\n", "IMAGE", r.filePath + ModLoader.fileComponent(r.texPath), "ATLAS", r.filePath + ModLoader.fileComponent(r.xmlPath));
    }
}
