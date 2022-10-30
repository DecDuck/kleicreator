package kleicreator.items.components;

import kleicreator.sdk.item.FieldData;
import kleicreator.sdk.item.ItemComponent;

import java.util.ArrayList;
import java.util.List;
// MASSIVE CREDIT TO -t- from Klei Forums

public class FiniteUses implements ItemComponent {
    @FieldData(name="Consumed on", tooltip = "The action that consumes a use")
    public Tool.Action action;
    @FieldData(name="Consumed amount", tooltip = "The amount of uses used every time you use the item")
    public int consumption;
    @FieldData(name="Max Uses", tooltip = "The starting amount of uses, and the max number of uses it can have")
    public int maxuses;

    @Override
    public List<String> ExportLines() {
        return new ArrayList<>();
    }
}
