package kleicreator.editor.nodes;

import kleicreator.recipes.Recipe;

public class NodeRecipe extends Node {
    public Recipe recipe;
    public NodeRecipe(Recipe recipe, Object userObject) {
        super(userObject);
        this.recipe = recipe;
    }
}
