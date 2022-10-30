package com.deepcore.kleicreator.items;

import com.deepcore.kleicreator.items.components.*;
import com.deepcore.kleicreator.sdk.item.ItemComponent;
import com.deepcore.kleicreator.sdk.logging.Logger;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.clapper.util.classutil.AndClassFilter;
import org.clapper.util.classutil.ClassFilter;
import org.clapper.util.classutil.ClassFinder;
import org.clapper.util.classutil.ClassInfo;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Item {

    public List<Entry<Boolean, ItemComponent>> itemComponents = new ArrayList<>();
    public String itemName = "New Item";
    public String itemId = "new_item";
    public int itemTexture = -1;
    public int stackSize = 100;
    public static List<Class<? extends ItemComponent>> registeredComponents = new ArrayList<>();
    static {
        Item.registeredComponents.clear();
        Reflections reflections = new Reflections("com.deepcore.kleicreator.items.components");
        Set<Class<? extends ItemComponent>> components = reflections.getSubTypesOf(ItemComponent.class);

        for(Class<? extends ItemComponent> component : components){
            registeredComponents.add(component);
        }
    }
    public Item() {
        for(Class<? extends ItemComponent> component : registeredComponents){
            try {
                AddComponent(component.getConstructor().newInstance());
            } catch (Exception e ) {
                Logger.Error(ExceptionUtils.getStackTrace(e));
            }
        }
    }

    public void AddComponent(ItemComponent c){
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
