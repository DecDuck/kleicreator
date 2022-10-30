package com.deepcore.kleicreator.items.components;

import com.deepcore.kleicreator.sdk.item.FieldData;
import com.deepcore.kleicreator.sdk.item.ItemComponent;

import java.util.ArrayList;
import java.util.List;
// MASSIVE CREDIT TO -t- from Klei Forums

public class Equippable implements ItemComponent {
    public enum EquipSlot {
        HANDS
    }
    @FieldData(name="Equip Slot", tooltip = "The slot in which the item is equipped")
    public EquipSlot equipSlot;
    @FieldData(name="Can Equip Stack", tooltip = "If you can equip multiple in a single slot")
    public boolean equipstack;
    @FieldData(name="Speed Multiplier", tooltip = "Speed multiplier when equipped")
    public double walkspeedmult;
    @FieldData(name="Dapperness", tooltip = "Sanity gain while equipped")
    public double dapperness;
    @FieldData(name="Insulated", tooltip = "Whether this item protects the user from lighting")
    public boolean isinsulated;
    @FieldData(name="Moisture from equipped", tooltip = "How much moisture you get from ")
    public double equippedmoisture;
    @FieldData(name="Restricted Tag", tooltip = "Tag required to equip this item")
    public String tag;
    @Override
    public List<String> ExportLines() {
        return new ArrayList<>();
    }
}
