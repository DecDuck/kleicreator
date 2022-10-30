package com.deepcore.kleicreator.items.components;

import com.deepcore.kleicreator.sdk.item.ItemComponent;

import java.util.ArrayList;
import java.util.List;

public class Axe implements ItemComponent {
    public double efficiency = 0.0;
    @Override
    public List<String> ExportLines() {
        return new ArrayList<>();
    }
}
