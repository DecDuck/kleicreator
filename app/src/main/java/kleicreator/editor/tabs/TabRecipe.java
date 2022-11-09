package kleicreator.editor.tabs;

import kleicreator.editor.frames.TabRecipeForm;
import kleicreator.recipes.Recipe;

public class TabRecipe extends Tab {
    public Recipe recipe;
    private final TabRecipeForm tabRecipeForm;

    public TabRecipe(Recipe recipe) {
        this.recipe = recipe;
        this.title = "Recipe: " + recipe.toString();
        tabRecipeForm = new TabRecipeForm(this, recipe);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TabRecipe) {
            return ((TabRecipe) obj).recipe.id.equals(recipe.id);
        } else {
            return false;
        }
    }
}
