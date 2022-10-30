package kleicreator.items;

import kleicreator.modloader.Mod;
import kleicreator.modloader.ModLoader;
import kleicreator.sdk.item.ItemComponent;
import kleicreator.sdk.logging.Logger;
import kleicreator.util.TooltipTreeRenderer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.HashMap;

import static kleicreator.util.TreeHelper.*;


public class ItemLoader extends ModLoader {

    public static HashMap<String, String> annotatedFieldMap = new HashMap<>();
    public static TooltipTreeRenderer notAdded = new TooltipTreeRenderer();
    public static TooltipTreeRenderer added = new TooltipTreeRenderer();

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
                            Item.Entry<Boolean, ItemComponent> c = item.itemComponents.get(i);
                            if (c.b.getClass().getSimpleName() == selPath.getLastPathComponent().toString()) {
                                c.a = !c.a;
                                Update();
                                return;
                            }
                        }
                        for (int i = 0; i < item.itemComponents.size(); i++) {
                            Item.Entry<Boolean, ItemComponent> c = item.itemComponents.get(i);
                            String value = selPath.getLastPathComponent().toString();
                            if(selPath.getPathComponent(1).toString() != c.b.getClass().getSimpleName()){
                                continue;
                            }
                            Logger.Debug("Using default case.");
                            String valueName = value.split(":")[0];

                            Field field = null;
                            try {
                                field = c.b.getClass().getField(valueName);
                            } catch (NoSuchFieldException ex) {
                                try {
                                    Logger.Log(valueName);
                                    field = c.b.getClass().getField(annotatedFieldMap.get(valueName));
                                } catch (NoSuchFieldException exc) {
                                    Logger.Debug("Unable to find value %s in class %s", valueName, c.b.getClass().getName());
                                    return;
                                }
                            }

                            try {
                                Object toSetValue = getValueFromUser(field.getType(), "New value for \"" + valueName + "\"", field.get(c.b));

                                if(toSetValue != null){
                                    field.set(c.b, toSetValue);
                                }else{
                                    //JOptionPane.showMessageDialog(modEditorFrame, "Unable to find suitable setter for value.", "Error in setting value", JOptionPane.WARNING_MESSAGE);
                                }

                            }catch (IllegalAccessException ex) {
                                Logger.Debug("Failed to find getter for value type");
                                return;
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
                            Item.Entry<Boolean, ItemComponent> c = item.itemComponents.get(i);
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
        ToolTipManager.sharedInstance().registerComponent(modEditor.getModItemComponentNotAdded());
        ToolTipManager.sharedInstance().registerComponent(modEditor.getModItemComponetsAdded());
        modEditor.getModItemComponentNotAdded().setCellRenderer(notAdded);
        modEditor.getModItemComponetsAdded().setCellRenderer(added);
        DefaultMutableTreeNode rootNotAdded = (DefaultMutableTreeNode) modelNotAdded.getRoot();
        DefaultMutableTreeNode rootAdded = (DefaultMutableTreeNode) modelAdded.getRoot();

        String expandedAdded = getExpansionState(modEditor.getModItemComponetsAdded());
        String expandedNotAdded = getExpansionState(modEditor.getModItemComponentNotAdded());

        rootNotAdded.removeAllChildren();
        rootAdded.removeAllChildren();

        for (Item.Entry<Boolean, ItemComponent> c : item.itemComponents) {
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
