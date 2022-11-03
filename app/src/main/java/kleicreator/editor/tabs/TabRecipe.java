package kleicreator.editor.tabs;

import kleicreator.recipes.Recipe;

import java.util.List;

public class TabRecipe extends Tab {
    public Recipe recipe;

    public TabRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
