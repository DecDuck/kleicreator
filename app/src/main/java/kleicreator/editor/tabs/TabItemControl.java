package kleicreator.editor.tabs;

import kleicreator.editor.frames.EditorMain;
import kleicreator.editor.frames.TabItemControlForm;

public class TabItemControl extends Tab{
    private final TabItemControlForm tabItemControlForm;
    public TabItemControl(EditorMain editorMain){
        this.title = "Items";
        tabItemControlForm = new TabItemControlForm(this, editorMain);
    }
}
