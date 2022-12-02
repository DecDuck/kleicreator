package kleicreator.resources.types;


import kleicreator.resources.Resource;
import kleicreator.resources.ResourceManager;

public class ResourceTexture extends Resource {
    public String texPath;
    public String xmlPath;

    public String filePath;
    public String displayUse;
    public ResourceManager.TextureType type;

    @Override
    public String toString() {
        return String.format("Texture (&s)", type);
    }
}
