package com.deepcore.kleicreator.items.components;

import com.deepcore.kleicreator.sdk.item.FieldName;
import com.deepcore.kleicreator.sdk.item.ItemComponent;

import java.util.ArrayList;
import java.util.List;

public class Edible implements ItemComponent {
    @FieldName(name="Health")
    public double health = 0.0;
    @FieldName(name="Sanity")
    public double sanity = 0.0;
    @FieldName(name="Hunger")
    public double hunger = 0.0;
    @Override
    public List<String> ExportLines() {
        return new ArrayList<>();
    }
}
