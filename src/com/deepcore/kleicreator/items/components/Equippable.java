package com.deepcore.kleicreator.items.components;

public class Equippable extends Component {
    public Place place = Place.Hand;

    public enum Place {
        Hat,
        Chest,
        Hand
    }
}
