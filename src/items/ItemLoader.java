package items;

import items.components.*;
import modloader.Mod;
import modloader.ModLoader;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;

import static util.TreeHelper.*;

public class ItemLoader extends ModLoader {

    public static void SetupAddedTree(JTree addedTree){
        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                ModLoader.SaveItem();
                int selRow = addedTree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = addedTree.getPathForLocation(e.getX(), e.getY());
                if(selRow != -1) {
                    if(e.getClickCount() == 1) {

                    }
                    else if(e.getClickCount() == 2) {
                        try {
                            if (Item.classMap.get(selPath.getLastPathComponent().toString()).equals(Axe.class)) {
                                Mod.items.get(modEditor.getModItemSelect().getSelectedIndex()).axeBool = false;
                                Update();
                                return;
                            }
                            if (Item.classMap.get(selPath.getLastPathComponent().toString()).equals(Edible.class)) {
                                Mod.items.get(modEditor.getModItemSelect().getSelectedIndex()).edibleBool = false;
                                Update();
                                return;
                            }
                            if (Item.classMap.get(selPath.getLastPathComponent().toString()).equals(Dapperness.class)) {
                                Mod.items.get(modEditor.getModItemSelect().getSelectedIndex()).dappernessBool = false;
                                Update();
                                return;
                            }
                            if (Item.classMap.get(selPath.getLastPathComponent().toString()).equals(Durability.class)) {
                                Mod.items.get(modEditor.getModItemSelect().getSelectedIndex()).durabilityBool = false;
                                Update();
                                return;
                            }
                            if (Item.classMap.get(selPath.getLastPathComponent().toString()).equals(Equippable.class)) {
                                Mod.items.get(modEditor.getModItemSelect().getSelectedIndex()).equipableBool = false;
                                Update();
                                return;
                            }
                            if (Item.classMap.get(selPath.getLastPathComponent().toString()).equals(Armor.class)) {
                                Mod.items.get(modEditor.getModItemSelect().getSelectedIndex()).armorBool = false;
                                Update();
                                return;
                            }
                        }catch(NullPointerException n){

                        }

                        if (Item.classMap.get(selPath.getPath()[1].toString()).equals(Axe.class)) {
                            Item item = Mod.items.get(modEditor.getModItemSelect().getSelectedIndex());
                            String value = selPath.getLastPathComponent().toString();
                            if(value.startsWith("efficiency")){
                                item.axe.efficiency = getFloat("Axe efficiency");
                            }
                            Update();
                            return;
                        }
                        if (Item.classMap.get(selPath.getPath()[1].toString()).equals(Edible.class)) {
                            Item item = Mod.items.get(modEditor.getModItemSelect().getSelectedIndex());
                            String value = selPath.getLastPathComponent().toString();
                            if(value.startsWith("health")){
                                item.edible.health = getFloat("Food health");
                            }
                            if(value.startsWith("sanity")){
                                item.edible.sanity = getFloat("Food sanity");
                            }
                            if(value.startsWith("hunger")){
                                item.edible.hunger = getFloat("Food hunger");
                            }
                            Update();
                            return;
                        }
                        if (Item.classMap.get(selPath.getPath()[1].toString()).equals(Dapperness.class)) {
                            Item item = Mod.items.get(modEditor.getModItemSelect().getSelectedIndex());
                            String value = selPath.getLastPathComponent().toString();
                            if(value.startsWith("rate")){
                                item.dapperness.rate = getFloat("Dapperness rate");
                            }
                            Update();
                            return;
                        }
                        if (Item.classMap.get(selPath.getPath()[1].toString()).equals(Durability.class)) {
                            Item item = Mod.items.get(modEditor.getModItemSelect().getSelectedIndex());
                            String value = selPath.getLastPathComponent().toString();
                            if(value.startsWith("durability")){
                                item.durability.durability = getFloat("Durability");
                            }
                            Update();
                            return;
                        }
                        if (Item.classMap.get(selPath.getPath()[1].toString()).equals(Equippable.class)) {
                            Item item = Mod.items.get(modEditor.getModItemSelect().getSelectedIndex());
                            String value = selPath.getLastPathComponent().toString();
                            if(value.startsWith("place")){
                                item.equippable.place = Equippable.Place.values()[getOption("Place", new Object[]{ "Hat", "Chest", "Hand" })];
                            }
                            Update();
                            return;
                        }
                        if (Item.classMap.get(selPath.getPath()[1].toString()).equals(Armor.class)) {
                            Item item = Mod.items.get(modEditor.getModItemSelect().getSelectedIndex());
                            String value = selPath.getLastPathComponent().toString();
                            if(value.startsWith("resistance")){
                                item.armor.resistance = getFloat("Resistance");
                            }
                            if(value.startsWith("maxCondition")){
                                item.armor.maxCondition = getFloat("Max Condition");
                            }
                            Update();
                            return;
                        }
                    }
                }
            }
        };
        addedTree.addMouseListener(ml);
    }

    public static void SetupNotAddedTree(JTree notAddedTree){
        MouseListener ml2 = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                ModLoader.SaveItem();
                int selRow = notAddedTree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = notAddedTree.getPathForLocation(e.getX(), e.getY());
                if(selRow != -1) {
                    if(e.getClickCount() == 1) {

                    }
                    else if(e.getClickCount() == 2) {
                        if(Item.classMap.get(selPath.getLastPathComponent().toString()).equals(Axe.class)){
                            Mod.items.get(modEditor.getModItemSelect().getSelectedIndex()).axeBool = true;
                            Update();
                        }
                        if(Item.classMap.get(selPath.getLastPathComponent().toString()).equals(Edible.class)){
                            Mod.items.get(modEditor.getModItemSelect().getSelectedIndex()).edibleBool = true;
                            Update();
                        }
                        if(Item.classMap.get(selPath.getLastPathComponent().toString()).equals(Dapperness.class)){
                            Mod.items.get(modEditor.getModItemSelect().getSelectedIndex()).dappernessBool = true;
                            Update();
                        }
                        if(Item.classMap.get(selPath.getLastPathComponent().toString()).equals(Durability.class)){
                            Mod.items.get(modEditor.getModItemSelect().getSelectedIndex()).durabilityBool = true;
                            Update();
                        }
                        if(Item.classMap.get(selPath.getLastPathComponent().toString()).equals(Equippable.class)){
                            Mod.items.get(modEditor.getModItemSelect().getSelectedIndex()).equipableBool = true;
                            Update();
                        }
                        if(Item.classMap.get(selPath.getLastPathComponent().toString()).equals(Armor.class)){
                            Mod.items.get(modEditor.getModItemSelect().getSelectedIndex()).armorBool = true;
                            Update();
                        }
                    }
                }
            }
        };
        notAddedTree.addMouseListener(ml2);
    }



    public static void UpdateTrees(Item item){
        DefaultTreeModel modelNotAdded = (DefaultTreeModel) modEditor.getModItemComponentNotAdded().getModel();
        DefaultTreeModel modelAdded = (DefaultTreeModel) modEditor.getModItemComponetsAdded().getModel();
        DefaultMutableTreeNode rootNotAdded = (DefaultMutableTreeNode) modelNotAdded.getRoot();
        DefaultMutableTreeNode rootAdded = (DefaultMutableTreeNode) modelAdded.getRoot();

        String expandedAdded = getExpansionState(modEditor.getModItemComponetsAdded());
        String expandedNotAdded = getExpansionState(modEditor.getModItemComponentNotAdded());

        rootNotAdded.removeAllChildren();
        rootAdded.removeAllChildren();

        if(item.armorBool){
            AddClassToTree(rootAdded, Armor.class, item.armor);
        }else{
            AddClassToTree(rootNotAdded, Armor.class, null);
        }

        if(item.edibleBool){
            AddClassToTree(rootAdded, Edible.class, item.edible);
        }else{
            AddClassToTree(rootNotAdded, Edible.class, null);
        }

        if(item.axeBool){
            AddClassToTree(rootAdded, Axe.class, item.axe);
        }else{
            AddClassToTree(rootNotAdded, Axe.class, null);
        }

        if(item.dappernessBool){
            AddClassToTree(rootAdded, Dapperness.class, item.dapperness);
        }else{
            AddClassToTree(rootNotAdded, Dapperness.class, null);
        }

        if(item.durabilityBool){
            AddClassToTree(rootAdded, Durability.class, item.durability);
        }else{
            AddClassToTree(rootNotAdded, Durability.class, null);
        }

        if(item.equipableBool){
            AddClassToTree(rootAdded, Equippable.class, item.equippable);
        }else{
            AddClassToTree(rootNotAdded, Equippable.class, null);
        }

        modelNotAdded.reload();
        modelAdded.reload();

        setExpansionState(expandedAdded, modEditor.getModItemComponetsAdded());
        setExpansionState(expandedNotAdded, modEditor.getModItemComponentNotAdded());
    }

}
