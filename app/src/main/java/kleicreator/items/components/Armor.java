package kleicreator.items.components;

import kleicreator.sdk.item.FieldData;
import kleicreator.sdk.item.ItemComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// MASSIVE CREDIT TO -t- from Klei Forums

public class Armor implements ItemComponent {
    @FieldData(name="Absorption", tooltip = "(0-1) Percentage of damage taken by armor")
    public double absorption;
    @FieldData(name="Indestructible", tooltip = "If the armor can break")
    public boolean is_indestructible;
    @FieldData(name="Durability", tooltip = "How much health the armor has")
    public double condition;
    @FieldData(name="Absorption Tags", tooltip = "A list of types of damage this armor blocks")
    public List<String> absorptiontags = new ArrayList<>();
    @FieldData(name="Weaknesses", tooltip = "A dictionary of types of damage and their multipliers")
    public Map<String, Double> weakness = new HashMap<>();

    @Override
    public List<String> ExportLines() {
        List<String> lines = new ArrayList<>();
        lines.add("inst:AddComponent(\"armor\")");
        if(is_indestructible){
            lines.add(String.format("inst.components.armor:InitIndestructible(%s)", absorption));
        }else{
            lines.add(String.format("inst.components.armor:InitCondition(%s, %s)", condition, absorption));
        }
        return lines;
    }
}
