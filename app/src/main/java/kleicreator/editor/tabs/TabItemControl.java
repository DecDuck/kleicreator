package kleicreator.editor.tabs;

import kleicreator.editor.frames.TabItemControlForm;

public class TabItemControl extends Tab{
    private TabItemControlForm tabItemControlForm;
    public TabItemControl(){
        this.title = "Items";
        tabItemControlForm = new TabItemControlForm(this);
    }
}
