package kleicreator.savesystem;


import kleicreator.items.Item;
import kleicreator.modloader.Mod;
import kleicreator.modloader.resources.Resource;
import kleicreator.modloader.resources.ResourceManager;
import kleicreator.recipes.Recipe;

import java.util.ArrayList;
import java.util.List;

public class SaveObject implements java.io.Serializable {
    public String modName;
    public String modAuthor;
    public String modDescription;
    public String modVersion;
    public int modIcon;
    public Mod.Game game;

    public List<Item> items = new ArrayList<Item>();
    public List<Resource> resources = new ArrayList<Resource>();
    public List<Recipe> recipes = new ArrayList<Recipe>();

    public SaveObject() {
        modName = Mod.modName;
        modAuthor = Mod.modAuthor;
        modDescription = Mod.modDescription;
        modVersion = Mod.modVersion;
        modIcon = Mod.modIcon;

        items = Mod.items;
        ResourceManager.GenerateResourceLists();
        resources = ResourceManager.resources;
        recipes = Mod.recipes;
        game = Mod.game;
    }

    public void LoadBack() {
        Mod.modName = modName;
        Mod.modAuthor = modAuthor;
        Mod.modDescription = modDescription;
        Mod.modVersion = modVersion;
        Mod.modIcon = modIcon;
        Mod.game = game;

        Mod.items.clear();
        for (int i = 0; i < items.size(); i++) {
            Mod.items.add(items.get(i));
        }
        Mod.recipes.clear();
        for (int i = 0; i < recipes.size(); i++) {
            Mod.recipes.add(recipes.get(i));
        }

        LoadResourcesList(resources);
        ResourceManager.GenerateResourceLists();
    }

    public void LoadResourcesList(List<Resource> a) {
        for (Resource r : a) {
            ResourceManager.LoadResource(r);
        }
    }
}
