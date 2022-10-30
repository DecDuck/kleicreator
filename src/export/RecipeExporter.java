package export;

import logging.Logger;
import modloader.Mod;
import recipes.Recipe;

import java.util.HashMap;
import java.util.Map;

public class RecipeExporter {

    public static String GenerateRecipeExport(){
        String output = "";

        for(Recipe r: Mod.recipes){
            output += GenerateRecipe(r) + "\n";
        }

        Logger.Log(output);

        return output;
    }

    private static String GenerateRecipe(Recipe r){
        String ingredients = "";
        String ingredientsTemplate = "Ingredient(\"%s\", %s), ";
        Map<String, Integer> ingredientsMap = new HashMap<String, Integer>();
        for(String ingr:r.ingredients){
            if(ingredientsMap.containsKey(ingr)){
                ingredientsMap.put(ingr, ingredientsMap.get(ingr)+1);
            }else{
                ingredientsMap.put(ingr, 1);
            }
        }

        for(Map.Entry<String, Integer> pair:ingredientsMap.entrySet()){
            ingredients += String.format(ingredientsTemplate, pair.getKey(), pair.getValue().toString());
        }

        ingredients = ingredients.substring(0, ingredients.length()-2);

        Logger.Log("Using ingredient string '%s'", ingredients);

        String recipeTemplate = "AddRecipe(\"%s\", { %s }, RECIPETABS.%s, TECH.%s)";
        String recipe = String.format(recipeTemplate, r.result, ingredients, r.tab.toString(), r.tech.toString());
        return recipe;
    }

}
