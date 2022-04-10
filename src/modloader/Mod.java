package modloader;

import items.Item;
import recipes.Recipe;

import java.util.*;

public class Mod {
    public static String modName;
    public static String modAuthor;
    public static String modDescription;
    public static String modVersion;
    public static int modIcon;

    public static String path;

    public static List<Item> items = new ArrayList<Item>();
    public static List<Recipe> recipes = new ArrayList<Recipe>();
}
