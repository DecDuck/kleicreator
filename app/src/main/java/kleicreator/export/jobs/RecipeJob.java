package kleicreator.export.jobs;

import kleicreator.export.Exporter;
import kleicreator.export.interfaces.Job;
import kleicreator.logging.Logger;
import kleicreator.modloader.Mod;
import kleicreator.recipes.Recipe;

import java.util.HashMap;
import java.util.Map;

public class RecipeJob implements Job {
    @Override
    public Map<String, String> run(Exporter exporter) throws Exception {
        StringBuilder output = new StringBuilder();

        for (Recipe r : Mod.recipes) {
            output.append(GenerateRecipe(r)).append("\n");
        }
        HashMap<String, String> export = new HashMap<>();
        export.put("recipe", output.toString());
        return export;
    }

    @Override
    public String prettyName() {
        return "Recipe Job";
    }

    private static String GenerateRecipe(Recipe r) {
        String ingredients = "";
        String ingredientsTemplate = "Ingredient(\"%s\", %s), ";
        Map<String, Integer> ingredientsMap = new HashMap<String, Integer>();
        for (String ingr : r.ingredients) {
            if (ingredientsMap.containsKey(ingr)) {
                ingredientsMap.put(ingr, ingredientsMap.get(ingr) + 1);
            } else {
                ingredientsMap.put(ingr, 1);
            }
        }

        for (Map.Entry<String, Integer> pair : ingredientsMap.entrySet()) {
            ingredients += String.format(ingredientsTemplate, pair.getKey(), pair.getValue().toString());
        }

        if(ingredientsMap.size() > 0){
            ingredients = ingredients.substring(0, ingredients.length() - 2);
        }


        Logger.Debug("Using ingredient string '%s'", ingredients);

        String recipeTemplate = "AddRecipe(\"%s\", { %s }, RECIPETABS.%s, TECH.%s)";
        String recipe = String.format(recipeTemplate, r.result, ingredients, r.tab.toString(), r.tech.toString());
        return recipe;
    }
}
