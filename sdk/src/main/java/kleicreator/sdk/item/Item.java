package kleicreator.sdk.item;

import kleicreator.sdk.item.ItemComponent;
import kleicreator.sdk.logging.Logger;
import org.reflections.Reflections;

import java.util.ArrayList;
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
        // This fancy reflection stuff is just for us. Plugins' components are registered when they're loaded in
        // Could probably be removed as it doesn't add much, but requires a whole new library
        Reflections reflections = new Reflections("kleicreator.items.components");
        Set<Class<? extends ItemComponent>> components = reflections.getSubTypesOf(ItemComponent.class);

        registeredComponents.addAll(components);
        Logger.Debug("Number of registered components: " + registeredComponents.size());
    }
    public Item() {
        for(Class<? extends ItemComponent> component : registeredComponents){
            try {
                AddComponent(component.getConstructor().newInstance());
            } catch (Exception e ) {
                Logger.Error(e);
            }
        }
    }

    public Item(String itemName, String itemId) {
        this();
        this.itemName = itemName;
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return itemName;
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