package kleicreator.modloader.classes;


import kleicreator.modloader.resources.Resource;

public class ResourceAnimation extends Resource {
    public String animFilePath;

    @Override
    public String toString() {
        return String.format("Animation (%s)", animFilePath);
    }
}
