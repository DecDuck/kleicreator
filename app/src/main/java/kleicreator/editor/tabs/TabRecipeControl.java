package kleicreator.editor.tabs;

import kleicreator.editor.frames.EditorMain;
import kleicreator.editor.frames.TabRecipeControlForm;

public class TabRecipeControl extends Tab {
    private TabRecipeControlForm tabRecipeControlForm;
    public TabRecipeControl(EditorMain editorMain){
        this.title = "Recipes";
        tabRecipeControlForm = new TabRecipeControlForm(this, editorMain);
    }
}
