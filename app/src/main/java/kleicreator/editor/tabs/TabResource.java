package kleicreator.editor.tabs;

import kleicreator.editor.frames.TabResourceForm;
import kleicreator.modloader.resources.Resource;

public class TabResource extends Tab {
    public Resource resource;
    private TabResourceForm tabResourceForm;

    public TabResource(Resource resource) {
        this.resource = resource;
        this.title = "Resource: " + resource.toString();
        tabResourceForm = new TabResourceForm(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TabResource){
            return ((TabResource) obj).resource.equals(resource);
        } else {
            return false;
        }
    }
}
