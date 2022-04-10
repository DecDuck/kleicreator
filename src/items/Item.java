package items;

import items.components.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Item implements java.io.Serializable {

    public static Map<String, Class> classMap;
    static {
        Map<String, Class> aMap = new HashMap<String, Class>();
        aMap.put("Axe", Axe.class);
        aMap.put("Edible", Edible.class);
        aMap.put("Dapperness", Dapperness.class);
        aMap.put("Durability", Durability.class);
        aMap.put("Equippable", Equippable.class);
        aMap.put("Armor", Armor.class);
        classMap = Collections.unmodifiableMap(aMap);
    }

    public String itemName = "New Item";
    public String itemId = "new_item";
    public int itemTexture;

    public boolean edibleBool = false;
    public Edible edible = new Edible();

    public boolean axeBool = false;
    public Axe axe = new Axe();

    public boolean dappernessBool = false;
    public Dapperness dapperness = new Dapperness();

    public boolean durabilityBool = false;
    public Durability durability = new Durability();

    public boolean equipableBool = false;
    public Equippable equippable = new Equippable();

    public boolean armorBool = false;
    public Armor armor = new Armor();
}
