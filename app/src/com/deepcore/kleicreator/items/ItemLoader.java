package com.deepcore.kleicreator.items;

import com.deepcore.kleicreator.items.components.*;
import com.deepcore.kleicreator.logging.Logger;
import com.deepcore.kleicreator.modloader.Mod;
import com.deepcore.kleicreator.modloader.ModLoader;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;

import static com.deepcore.kleicreator.util.TreeHelper.*;

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
                            if (false) { // Add specific cases here, although it's unlikely you'll need to cause of the gnarly function getValueFromUser

                            }else{
                                if(selPath.getPathComponent(1).toString() != c.b.getClass().getSimpleName()){
                                    continue;
                                }
                                Logger.Debug("Using default case.");
                                String valueName = value.split(" ")[0];
                                valueName = valueName.substring(0, valueName.length()-1);
                                try {
                                    Field f = c.b.getClass().getField(valueName);

                                    Object toSetValue = getValueFromUser(f.getType(), "New value for \"" + valueName + "\"");

                                    if(toSetValue != null){
                                        f.set(c.b, toSetValue);
                                    }else{
                                        JOptionPane.showMessageDialog(modEditorFrame, "Unable to find suitable setter for value.", "Error in setting value", JOptionPane.WARNING_MESSAGE);
                                    }

                                } catch (NoSuchFieldException ex) {
                                    Logger.Debug("Unable to find value %s in class %s", valueName, c.b.getClass().getName());
                                    return;
                                } catch (IllegalAccessException ex) {
                                    Logger.Debug("Failed to find getter for value type");
                                    return;
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
