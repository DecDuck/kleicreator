package com.deepcore.kleicreator.items.components;

import com.deepcore.kleicreator.sdk.item.FieldData;
import com.deepcore.kleicreator.sdk.item.ItemComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// MASSIVE CREDIT TO -t- from Klei Forums

public class Armor implements ItemComponent {
    @FieldData(name="Absorption", tooltip = "(0-1) Percentage of damage taken by armor")
    public double absorption;
    @FieldData(name="Indestructable", tooltip = "If the armor can break")
    public boolean is_indestructable;
    @FieldData(name="Durability", tooltip = "How much health the armor has")
    public double condition;
    @FieldData(name="Absorption Tags", tooltip = "A list of types of damage this armor blocks")
    public List<String> absorptiontags;
    @FieldData(name="Weaknesses", tooltip = "A dictionary of types of damage and their multipliers")
    public Map<String, Double> weakness;

    @Override
    public List<String> ExportLines() {
        List<String> lines = new ArrayList<>();
        lines.add("inst:AddComponent(\"armor\")");
        if(is_indestructable){
            lines.add(String.format("inst.components.armor:InitIndestructible(%s)", absorption));
        }else{
            lines.add(String.format("inst.components.armor:InitCondition(%s, %s)", condition, absorption));
        }
        return lines;
    }
}
