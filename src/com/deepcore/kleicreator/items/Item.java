package com.deepcore.kleicreator.items;

import com.deepcore.kleicreator.items.components.*;
import com.deepcore.kleicreator.logging.Logger;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Item {

    public List<Entry<Boolean, Component>> itemComponents = new ArrayList<>();
    public String itemName = "New Item";
    public String itemId = "new_item";
    public int itemTexture = -1;
    public int stackSize = 100;
    public Item() {
        AddComponent(new Armor());
        AddComponent(new Axe());
        AddComponent(new Dapperness());
        AddComponent(new Durability());
        AddComponent(new Edible());
        AddComponent(new Equippable());
        AddComponent(new Waterproof());
    }

    public void AddComponent(Component c){
        itemComponents.add(new Entry<>(false, c));
    }

    public class Entry<T, Y> {
        public T a;
        public Y b;

        public Entry(T a, Y b) {
            this.a = a;
            this.b = b;
        }
    }
}
