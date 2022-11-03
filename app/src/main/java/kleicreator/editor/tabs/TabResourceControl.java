package kleicreator.editor.tabs;

import kleicreator.editor.frames.TabResourceControlForm;

public class TabResourceControl extends Tab {
    private TabResourceControlForm tabResourceControlForm;
    public TabResourceControl(){
        this.title = "Resources";
        tabResourceControlForm = new TabResourceControlForm(this);
    }
}
