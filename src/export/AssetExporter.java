package export;

import logging.Logger;
import modloader.ModLoader;
import modloader.resources.Resource;

public class AssetExporter {
    public static String ExportAsset(Resource r){
        if(!r.isTexture){
            Logger.Log("It's not a texture :(");
            return "";
        }
        return String.format("    Asset(\"%s\", \"%s\"),\n    Asset(\"%s\", \"%s\"),\n", "IMAGE", r.filePath + ModLoader.fileComponent(r.texture.texPath), "ATLAS", r.filePath + ModLoader.fileComponent(r.texture.xmlPath));
    }
}
