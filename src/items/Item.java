package items;

import items.components.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item {

    public class Entry<T, Y>{
        public T a;
        public Y b;

        public Entry(T a, Y b){
            this.a = a;
            this.b = b;
        }
    }

    public Item(){
        itemComponents.add(new Entry<>(false, new Armor()));
        itemComponents.add(new Entry<>(false, new Axe()));
        itemComponents.add(new Entry<>(false, new Dapperness()));
        itemComponents.add(new Entry<>(false, new Durability()));
        itemComponents.add(new Entry<>(false, new Edible()));
        itemComponents.add(new Entry<>(false, new Equippable()));
    }

    public List<Entry<Boolean, Component>> itemComponents = new ArrayList<>();

    public String itemName = "New Item";
    public String itemId = "new_item";
    public int itemTexture = -1;
    public int stackSize = 100;
}
