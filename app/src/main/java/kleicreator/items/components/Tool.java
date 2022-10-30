package kleicreator.items.components;

import kleicreator.sdk.item.FieldData;
import kleicreator.sdk.item.ItemComponent;

import java.util.ArrayList;
import java.util.List;
// MASSIVE CREDIT TO -t- from Klei Forums

public class Tool implements ItemComponent {

    // These are TOOLACTIONS in the constants file
    public enum Action {
        CHOP,
        DIG,
        HAMMER,
        MINE,
        NET,
        PLAY,
        UNSADDLE,
        REACH_HIGH
    }
    @FieldData(name="Action", tooltip = "What this tool does")
    public Action action;
    @FieldData(name="Efficiency", tooltip = "How quickly this tool does what it does")
    public double effectiveness;

    @Override
    public List<String> ExportLines() {
        List<String> lines = new ArrayList<>();

        lines.add("inst:AddComponent(\"tool\")");
        lines.add("inst.components.tool:SetAction(TOOLACTIONS."+action+", "+effectiveness+")");

        return lines;
    }
}
