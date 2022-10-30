package com.deepcore.kleicreator.items.components;

import com.deepcore.kleicreator.sdk.item.ItemComponent;

import java.util.ArrayList;
import java.util.List;

public class Edible implements ItemComponent {
    public double health = 0.0;
    public double sanity = 0.0;
    public double hunger = 0.0;
    @Override
    public List<String> ExportLines() {
        return new ArrayList<>();
    }
}
