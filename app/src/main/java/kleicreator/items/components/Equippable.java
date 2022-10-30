package kleicreator.items.components;

import kleicreator.sdk.item.FieldData;
import kleicreator.sdk.item.ItemComponent;

import java.util.ArrayList;
import java.util.List;
// MASSIVE CREDIT TO -t- from Klei Forums

public class Equippable implements ItemComponent {
    public enum EquipSlot {
        HANDS,
        HEAD,
        BODY
    }
    @FieldData(name="Equip Slot", tooltip = "The slot in which the item is equipped")
    public EquipSlot equipSlot;
    @FieldData(name="Can Equip Stack", tooltip = "If you can equip multiple in a single slot")
    public boolean equipstack;
    @FieldData(name="Speed Multiplier", tooltip = "Speed multiplier when equipped")
    public double walkspeedmult;
    @FieldData(name="Dapperness", tooltip = "Sanity gain while equipped")
    public double dapperness;
    @FieldData(name="Insulated", tooltip = "Whether this item protects the user from lighting")
    public boolean isinsulated;
    @FieldData(name="Moisture from equipped", tooltip = "How much moisture you get from this item")
    public double equippedmoisture;
    @FieldData(name="Maximum moisture from equipped", tooltip = "How much moisture you get from this item (maximum)")
    public double maxequippedmoisture;
    @FieldData(name="Restricted Tag", tooltip = "Tag required to equip this item")
    public String tag;
    @Override
    public List<String> ExportLines() {
        List<String> lines = new ArrayList<>();

        lines.add("inst:AddComponent(\"equippable\")");
        lines.add("inst.components.equippable.equipslot = EQUIPSLOT."+equipSlot.toString());
        if(equipstack){
            lines.add("inst.components.equippable.equipstack = true");
        }
        lines.add("inst.components.equippable.walkspeedmult = "+walkspeedmult);
        lines.add("inst.components.equippable.dapperness = "+dapperness);
        if(isinsulated){
            lines.add("inst.components.equippable.insulated = true");
        }
        lines.add("inst.components.equippable.equippedmoisture = "+equippedmoisture);
        lines.add("inst.components.equippable.maxequippedmoisture = "+maxequippedmoisture);
        lines.add("inst.components.equippable.restrictedtag = "+tag);

        /*
        inst.components.equippable:SetOnEquip(onequip)
        inst.components.equippable:SetOnUnequip(onunequip)
        inst.components.equippable:SetOnPocket(onget)
        */

        return lines;
    }
}
