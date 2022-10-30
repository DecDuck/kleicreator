package com.deepcore.kleicreator.items.components;

import com.deepcore.kleicreator.sdk.item.ItemComponent;

import java.util.ArrayList;
import java.util.List;

public class Armor implements ItemComponent {
    public double resistance = 0.0;
    public double maxCondition = 0.0;

    @Override
    public List<String> ExportLines() {
        return new ArrayList<>();
    }
}
