package kleicreator.modloader;


import kleicreator.items.AllItems;
import kleicreator.items.Item;
import kleicreator.recipes.Recipe;

import java.util.ArrayList;
import java.util.List;

public class Mod {
    public static String modName;
    public static String modAuthor;
    public static String modDescription;
    public static String modVersion;
    public static int modIcon;
    public static String path;
    public static List<Item> items = new ArrayList<Item>();
    public static List<Recipe> recipes = new ArrayList<Recipe>();
    public static Item questionItem = new Item("???", "id");

    public static String escapedModName() {
        return modName.replaceAll("[$&+,:;=?@#|'<>.^*()%! -]", "").toLowerCase();
    }

    public static List<Item> GetAllItems(){
        List<Item> allItems = new ArrayList<>();
        allItems.addAll(items);
        allItems.addAll(AllItems.allItems);
        return allItems;
    }

    public static Item GetItemById(String id){
        List<Item> allItems = GetAllItems();
        for(Item i : allItems){
            if(i.itemId.equals(id)){
                return i;
            }
        }
        return questionItem;
    }

    public static Item GetItemByName(String name){
        List<Item> allItems = GetAllItems();
        for(Item i : allItems){
            if(i.itemName.equals(name)){
                return i;
            }
        }
        return questionItem;
    }
}
