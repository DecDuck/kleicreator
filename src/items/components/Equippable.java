package items.components;

public class Equippable extends Component {
    public enum Place{
        Hat,
        Chest,
        Hand
    }

    public Place place = Place.Hand;
}
