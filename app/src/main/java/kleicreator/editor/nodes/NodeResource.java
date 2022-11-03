package kleicreator.editor.nodes;

import kleicreator.modloader.resources.Resource;

public class NodeResource extends Node{
    public Resource resource;
    public NodeResource(Resource resource, Object userObject) {
        super(userObject);
        this.resource = resource;
    }
}
