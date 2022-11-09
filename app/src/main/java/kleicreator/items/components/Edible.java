package kleicreator.items.components;

import kleicreator.items.FieldData;
import kleicreator.items.ItemComponent;

import java.util.ArrayList;
import java.util.List;

// MASSIVE CREDIT TO -t- from Klei Forums

public class Edible implements ItemComponent {

    public enum Foodtype {
        GENERIC,
        MEAT,
        VEGGIE,
        ELEMENTAL,
        GEARS,
        HORRIBLE,
        INSECT,
        SEEDS,
        BERRY,
        RAW,
        BURNT,
        ROUGHAGE,
        WOOD,
        GOODIES,
        MONSTER
    }

    @FieldData(name="Foodtype", tooltip = "Type of food")
    public Foodtype foodtype;
    @FieldData(name="Secondary Foodtype", tooltip = "Another food type")
    public Foodtype secondaryfoodtype;
    @FieldData(name="Health", tooltip = "Amount of health restored when eaten")
    public double health;
    @FieldData(name="Hunger", tooltip = "Amount of hunger restored when eaten")
    public double hunger;
    @FieldData(name="Sanity", tooltip = "Amount of sanity restored when eaten")
    public double sanity;
    @FieldData(name="Degrades with spoilage", tooltip = "If the values change when it spoils")
    public boolean degrades_with_spoilage;
    @FieldData(name="Stale Health", tooltip = "Amount of health restored when eaten it's stale")
    public double stale_health;
    @FieldData(name="Stale Hunger", tooltip = "Amount of hunger restored when eaten it's stale")
    public double stale_hunger;
    @FieldData(name="Spoiled Health", tooltip = "Amount of health restored when eaten it's spoiled")
    public double spoiled_health;
    @FieldData(name="Spoiled Hunger", tooltip = "Amount of hunger restored when eaten it's spoiled")
    public double spoiled_hunger;
    @FieldData(name="Temperature Amount", tooltip = "How much temperature is added when eaten")
    public double temperature_delta;
    @FieldData(name="Temperature Length", tooltip = "How long the temperature takes effect for")
    public double temperature_duration;

    @Override
    public List<String> ExportLines() {

        List<String> lines = new ArrayList<>();
        lines.add("inst:AddComponent(\"edible\")");
        lines.add("inst.components.edible.foodtype = FOODTYPE."+foodtype.toString());
        lines.add("inst.components.edible.secondaryfoodtype = FOODTYPE."+foodtype.toString());
        lines.add("inst.components.edible.healthvalue = "+health);
        lines.add("inst.components.edible.hungervalue = "+hunger);
        lines.add("inst.components.edible.sanityvalue = "+sanity);
        if(degrades_with_spoilage){
            lines.add("inst.components.edible.degrades_with_spoilage = true");
            lines.add("inst.components.edible.stale_health = "+stale_health);
            lines.add("inst.components.edible.spoiled_health = "+spoiled_health);
            lines.add("inst.components.edible.stale_hunger = "+stale_hunger);
            lines.add("inst.components.edible.spoiled_hunger = "+spoiled_hunger);

        }
        // inst.components.edible:SetOnEatenFn(oneaten)
        // inst.components.edible:SetGetHealthFn(healthfn)

        return lines;
    }
}
