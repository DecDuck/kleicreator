package kleicreator.editor.tabs;

import kleicreator.editor.frames.TabRecipeForm;
import kleicreator.recipes.Recipe;

import java.util.List;

public class TabRecipe extends Tab {
    public Recipe recipe;
    private TabRecipeForm tabRecipeForm;
    public TabRecipe(Recipe recipe) {
        this.recipe = recipe;
        this.title = recipe.toString();
        tabRecipeForm = new TabRecipeForm(this);
    }
}
