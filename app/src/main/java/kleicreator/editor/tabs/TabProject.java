package kleicreator.editor.tabs;

import kleicreator.editor.frames.TabProjectForm;

public class TabProject extends Tab {
    private TabProjectForm tabProjectForm;
    public TabProject(){
        tabProjectForm = new TabProjectForm(this);
        this.title = "Project";
    }
}
