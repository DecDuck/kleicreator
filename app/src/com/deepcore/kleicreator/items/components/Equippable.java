package com.deepcore.kleicreator.items.components;

import com.deepcore.kleicreator.sdk.item.ItemComponent;

import java.util.ArrayList;
import java.util.List;

public class Equippable implements ItemComponent {
    public Place place = Place.Hand;

    public enum Place {
        Hat,
        Chest,
        Hand
    }
    @Override
    public List<String> ExportLines() {
        return new ArrayList<>();
    }
}
