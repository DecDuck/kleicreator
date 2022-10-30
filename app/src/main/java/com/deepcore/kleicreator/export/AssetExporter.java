package com.deepcore.kleicreator.export;

import com.deepcore.kleicreator.modloader.ModLoader;
import com.deepcore.kleicreator.modloader.classes.ResourceTexture;

public class AssetExporter {
    public static String ExportAsset(ResourceTexture r) {
        return String.format("    Asset(\"%s\", \"%s\"),\n    Asset(\"%s\", \"%s\"),\n", "IMAGE", r.filePath + ModLoader.fileComponent(r.texPath), "ATLAS", r.filePath + ModLoader.fileComponent(r.xmlPath));
    }
}
