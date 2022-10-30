package com.deepcore.kleicreator.items.components;

import com.deepcore.kleicreator.sdk.item.FieldData;
import com.deepcore.kleicreator.sdk.item.ItemComponent;

import java.util.ArrayList;
import java.util.List;
// MASSIVE CREDIT TO -t- from Klei Forums

public class Tool implements ItemComponent {

    public enum Action {
        CHOP,
        MINE,
        DIG,
        HAMMER
    }
    @FieldData(name="Action", tooltip = "What this tool does")
    public Action action;
    @FieldData(name="Efficiency", tooltip = "How quickly this tool does what it does")
    public double effectiveness;

    @Override
    public List<String> ExportLines() {
        return new ArrayList<>();
    }
}
