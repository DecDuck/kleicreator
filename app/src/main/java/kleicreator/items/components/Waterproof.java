package kleicreator.items.components;

import kleicreator.sdk.item.FieldData;
import kleicreator.sdk.item.ItemComponent;

import java.util.ArrayList;
import java.util.List;
// MASSIVE CREDIT TO -t- from Klei Forums

public class Waterproof implements ItemComponent {
    @FieldData(name="Effectiveness", tooltip = "(0-1) How much it protects from water.")
    public double effectiveness = 0.0;
    @Override
    public List<String> ExportLines() {
        List<String> lines = new ArrayList<>();

        lines.add("inst:AddComponent(\"waterproofer\")");
        lines.add("inst.components.waterproofer:SetEffectiveness("+effectiveness+")");

        return lines;
    }
}
