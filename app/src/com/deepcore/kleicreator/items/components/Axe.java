package com.deepcore.kleicreator.items.components;

import com.deepcore.kleicreator.sdk.item.FieldName;
import com.deepcore.kleicreator.sdk.item.ItemComponent;

import java.util.ArrayList;
import java.util.List;

public class Axe implements ItemComponent {
    @FieldName(name="Speed")
    public double efficiency = 0.0;
    @Override
    public List<String> ExportLines() {
        return new ArrayList<>();
    }
}
