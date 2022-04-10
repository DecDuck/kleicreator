package recipes;

import java.util.List;

public class Recipe {

    public String result;
    public List<String> ingredients;
    public String tag;
    public String workstation;

    public Recipe(){

    }

    public Recipe(String result, List<String> ingredients, String tag, String workstation) {
        this.result = result;
        this.ingredients = ingredients;
        this.tag = tag;
        this.workstation = workstation;
    }
}
