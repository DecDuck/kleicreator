package com.deepcore.kleicreator.savesystem;

import com.deepcore.kleicreator.items.Item;
import com.deepcore.kleicreator.modloader.Mod;
import com.deepcore.kleicreator.modloader.resources.Resource;
import com.deepcore.kleicreator.modloader.resources.ResourceManager;
import com.deepcore.kleicreator.recipes.Recipe;

import java.util.ArrayList;
import java.util.List;

public class SaveObject implements java.io.Serializable {
    public String modName;
    public String modAuthor;
    public String modDescription;
    public String modVersion;
    public int modIcon;

    public List<Item> items = new ArrayList<Item>();
    public List<Resource> resources = new ArrayList<Resource>();
    public List<Recipe> recipes = new ArrayList<Recipe>();

    public SaveObject() {
        modName = Mod.modName;
        modAuthor = Mod.modAuthor;
        modDescription = Mod.modDescription;
        modVersion = Mod.modAuthor;
        modIcon = Mod.modIcon;

        items = Mod.items;
        ResourceManager.GenerateResourceLists();
        resources = ResourceManager.resources;
        recipes = Mod.recipes;
    }

    public void LoadBack() {
        Mod.modName = modName;
        Mod.modAuthor = modAuthor;
        Mod.modDescription = modDescription;
        Mod.modVersion = modVersion;
        Mod.modIcon = modIcon;

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
