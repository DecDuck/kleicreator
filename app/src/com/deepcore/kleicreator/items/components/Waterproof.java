package com.deepcore.kleicreator.items.components;

import com.deepcore.kleicreator.sdk.item.FieldName;
import com.deepcore.kleicreator.sdk.item.ItemComponent;

import java.util.ArrayList;
import java.util.List;

public class Waterproof implements ItemComponent {
    @FieldName(name="Effectiveness")
    public double percentage = 0.0;
    @Override
    public List<String> ExportLines() {
        return new ArrayList<>();
    }
}
