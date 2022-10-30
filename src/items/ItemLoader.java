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

import static util.TreeHelper.*;

public class ItemLoader extends ModLoader {

    public static void SetupAddedTree(JTree addedTree) {
        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                ModLoader.SaveItem();
                int selRow = addedTree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = addedTree.getPathForLocation(e.getX(), e.getY());
                if (selRow != -1) {
                    if (e.getClickCount() == 1) {

                    } else if (e.getClickCount() == 2) {
                        Item item = Mod.items.get(modEditor.getModItemSelect().getSelectedIndex());
                        for (int i = 0; i < item.itemComponents.size(); i++) {
                            Item.Entry<Boolean, Component> c = item.itemComponents.get(i);
                            if (c.b.getClass().getSimpleName() == selPath.getLastPathComponent().toString()) {
                                c.a = !c.a;
                                Update();
                                return;
                            }
                        }
                        for (int i = 0; i < item.itemComponents.size(); i++) {
                            Item.Entry<Boolean, Component> c = item.itemComponents.get(i);
                            String value = selPath.getLastPathComponent().toString();
                            if (c.b instanceof Armor) {
                                if (value.startsWith("resistance")) {
                                    ((Armor) c.b).resistance = getFloat("Resistance");
                                }
                                if (value.startsWith("maxCondition")) {
                                    ((Armor) c.b).maxCondition = getFloat("Max Condition");
                                }
                            } else if (c.b instanceof Axe) {
                                if (value.startsWith("efficiency")) {
                                    ((Axe) c.b).efficiency = getFloat("Axe efficiency");
                                }
                            } else if (c.b instanceof Dapperness) {
                                if (value.startsWith("rate")) {
                                    ((Dapperness) c.b).rate = getFloat("Dapperness rate");
                                }
                            } else if (c.b instanceof Durability) {
                                if (value.startsWith("durability")) {
                                    ((Durability) c.b).durability = getFloat("Durability");
                                }
                            } else if (c.b instanceof Edible) {
                                if (value.startsWith("health")) {
                                    ((Edible) c.b).health = getFloat("Food health");
                                }
                                if (value.startsWith("sanity")) {
                                    ((Edible) c.b).sanity = getFloat("Food sanity");
                                }
                                if (value.startsWith("hunger")) {
                                    ((Edible) c.b).hunger = getFloat("Food hunger");
                                }
                            } else if (c.b instanceof Equippable) {
                                if (value.startsWith("place")) {
                                    Object[] options = new Object[Equippable.Place.values().length];
                                    for (int x = 0; x < options.length; x++) {
                                        options[x] = Equippable.Place.values()[x];
                                    }
                                    ((Equippable) c.b).place = Equippable.Place.values()[getOption("Place", options)];
                                }
                            }
                            item.itemComponents.set(i, c);
                        }
                        Update();
                        return;
                    }
                }
            }
        };
        addedTree.addMouseListener(ml);
    }

    public static void SetupNotAddedTree(JTree notAddedTree) {
        MouseListener ml2 = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                ModLoader.SaveItem();
                int selRow = notAddedTree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = notAddedTree.getPathForLocation(e.getX(), e.getY());
                if (selRow != -1) {
                    if (e.getClickCount() == 1) {

                    } else if (e.getClickCount() == 2) {
                        Item item = Mod.items.get(modEditor.getModItemSelect().getSelectedIndex());
                        for (int i = 0; i < item.itemComponents.size(); i++) {
                            Item.Entry<Boolean, Component> c = item.itemComponents.get(i);
                            if (c.b.getClass().getSimpleName() == selPath.getLastPathComponent().toString()) {
                                c.a = !c.a;
                                Update();
                                return;
                            }
                        }
                    }
                }
            }
        };
        notAddedTree.addMouseListener(ml2);
    }


    public static void UpdateTrees(Item item) {
        DefaultTreeModel modelNotAdded = (DefaultTreeModel) modEditor.getModItemComponentNotAdded().getModel();
        DefaultTreeModel modelAdded = (DefaultTreeModel) modEditor.getModItemComponetsAdded().getModel();
        DefaultMutableTreeNode rootNotAdded = (DefaultMutableTreeNode) modelNotAdded.getRoot();
        DefaultMutableTreeNode rootAdded = (DefaultMutableTreeNode) modelAdded.getRoot();

        String expandedAdded = getExpansionState(modEditor.getModItemComponetsAdded());
        String expandedNotAdded = getExpansionState(modEditor.getModItemComponentNotAdded());

        rootNotAdded.removeAllChildren();
        rootAdded.removeAllChildren();

        for (Item.Entry<Boolean, Component> c : item.itemComponents) {
            if (c.a) {
                AddClassToTree(rootAdded, c.b.getClass(), c.b);
            } else {
                AddClassToTree(rootNotAdded, c.b.getClass(), null);
            }
        }

        modelNotAdded.reload();
        modelAdded.reload();

        setExpansionState(expandedAdded, modEditor.getModItemComponetsAdded());
        setExpansionState(expandedNotAdded, modEditor.getModItemComponentNotAdded());
    }

}
