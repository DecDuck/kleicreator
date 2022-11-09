package kleicreator.editor.frames;

import com.google.gson.Gson;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import kleicreator.editor.listeners.TextFieldBinding;
import kleicreator.frames.ListEditor;
import kleicreator.sdk.item.FieldData;
import kleicreator.sdk.item.Item;
import kleicreator.sdk.item.ItemComponent;
import kleicreator.sdk.logging.Logger;
import kleicreator.util.TooltipTreeNode;
import kleicreator.util.TooltipTreeRenderer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static kleicreator.util.TreeHelper.*;
import static kleicreator.util.TreeHelper.setExpansionState;

public class TabItemForm {
    private JPanel tabItemPanel;
    private JTree notAddedTree;
    private JTree addedTree;
    private JTextField itemName;
    private JTextField itemID;
    private JComboBox itemTexture;
    private JSpinner itemStackSize;
    private Item item;

    private TooltipTreeRenderer notAddedTooltipRenderer = new TooltipTreeRenderer();
    private TooltipTreeRenderer addedTooltipRenderer = new TooltipTreeRenderer();

    private HashMap<String, String> annotatedFieldMap = new HashMap<>();

    public TabItemForm(JPanel tab, Item item) {
        tab.add(tabItemPanel);
        this.item = item;

        itemName.setText(item.itemName);
        itemID.setText(item.itemId);
        itemStackSize.setValue(item.stackSize);

        new TextFieldBinding(itemName, item::setItemName);
        new TextFieldBinding(itemID, item::setItemId);
        itemStackSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                item.stackSize = (int) itemStackSize.getValue();
            }
        });
        SetupAddedTree(addedTree);
        SetupNotAddedTree(notAddedTree);
        UpdateTrees();
    }

    public void UpdateTrees() {
        DefaultTreeModel modelNotAdded = (DefaultTreeModel) notAddedTree.getModel();
        DefaultTreeModel modelAdded = (DefaultTreeModel) addedTree.getModel();
        ToolTipManager.sharedInstance().registerComponent(notAddedTree);
        ToolTipManager.sharedInstance().registerComponent(addedTree);
        notAddedTree.setCellRenderer(notAddedTooltipRenderer);
        addedTree.setCellRenderer(addedTooltipRenderer);
        DefaultMutableTreeNode rootNotAdded = (DefaultMutableTreeNode) modelNotAdded.getRoot();
        DefaultMutableTreeNode rootAdded = (DefaultMutableTreeNode) modelAdded.getRoot();

        String expandedAdded = getExpansionState(addedTree);
        String expandedNotAdded = getExpansionState(notAddedTree);

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

        setExpansionState(expandedAdded, addedTree);
        setExpansionState(expandedNotAdded, notAddedTree);
    }

    /* I have no idea what the hell any of this does, but it works, so I'm not going near it */
    public void SetupAddedTree(JTree addedTree) {
        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selRow = addedTree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = addedTree.getPathForLocation(e.getX(), e.getY());
                if (selRow != -1) {
                    if (e.getClickCount() == 1) {

                    } else if (e.getClickCount() == 2) {
                        if (selPath.getLastPathComponent() == null || selPath.getLastPathComponent() == selPath.getPathComponent(0)) {
                            return;
                        } else if (selPath.getLastPathComponent() == selPath.getPathComponent(1)) {
                            // We're enabling/disabling a component
                            for (int i = 0; i < item.itemComponents.size(); i++) {
                                Item.Entry<Boolean, ItemComponent> c = item.itemComponents.get(i);
                                if (c.b.getClass().getSimpleName().equals(selPath.getLastPathComponent().toString())) {
                                    c.a = !c.a;
                                    UpdateTrees();
                                    return;
                                }
                            }
                        } else if (selPath.getLastPathComponent() == selPath.getPathComponent(2)) {
                            // We're modifying a value
                            for (int i = 0; i < item.itemComponents.size(); i++) {
                                Item.Entry<Boolean, ItemComponent> c = item.itemComponents.get(i);
                                String value = selPath.getLastPathComponent().toString();
                                if (selPath.getPathComponent(1).toString() != c.b.getClass().getSimpleName()) {
                                    continue;
                                }
                                String valueName = value.split(":")[0];

                                Field field = null;
                                try {
                                    field = c.b.getClass().getField(valueName);
                                } catch (NoSuchFieldException ex) {
                                    try {
                                        field = c.b.getClass().getField(annotatedFieldMap.get(valueName));
                                    } catch (NoSuchFieldException exc) {
                                        Logger.Debug("Unable to find value %s in class %s", valueName, c.b.getClass().getName());
                                        return;
                                    }
                                }

                                try {
                                    Object toSetValue = ValueSelector(field.getType(), "New value for \"" + valueName + "\"", field.get(c.b));

                                    if (toSetValue != null) {
                                        field.set(c.b, toSetValue);
                                    } else {
                                        //JOptionPane.showMessageDialog(modEditorFrame, "Unable to find suitable setter for value.", "Error in setting value", JOptionPane.WARNING_MESSAGE);
                                    }

                                } catch (IllegalAccessException ex) {
                                    Logger.Debug("Failed to find getter for value type");
                                    return;
                                }
                                item.itemComponents.set(i, c);
                            }
                            UpdateTrees();
                        }
                    }
                }
            }
        };
        addedTree.addMouseListener(ml);
    }

    /* I have no idea what the hell any of this does, but it works, so I'm not going near it */
    public void SetupNotAddedTree(JTree notAddedTree) {
        MouseListener ml2 = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selRow = notAddedTree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = notAddedTree.getPathForLocation(e.getX(), e.getY());
                if (selRow != -1) {
                    if (e.getClickCount() == 1) {

                    } else if (e.getClickCount() == 2) {
                        for (int i = 0; i < item.itemComponents.size(); i++) {
                            Item.Entry<Boolean, ItemComponent> c = item.itemComponents.get(i);
                            if (c.b.getClass().getSimpleName() == selPath.getLastPathComponent().toString()) {
                                c.a = !c.a;
                                UpdateTrees();
                                return;
                            }
                        }
                    }
                }
            }
        };
        notAddedTree.addMouseListener(ml2);
    }

    public <T> void AddClassToTree(DefaultMutableTreeNode root, Class toAdd, T values) {
        Field[] fields = toAdd.getFields();
        TooltipTreeNode node = new TooltipTreeNode(toAdd.getSimpleName(), "");

        for (Field f : fields) {
            String name = f.getName();
            String tooltip = "";
            if (f.isAnnotationPresent(FieldData.class)) {
                name = f.getAnnotation(FieldData.class).name();
                tooltip = f.getAnnotation(FieldData.class).tooltip();
                annotatedFieldMap.put(f.getAnnotation(FieldData.class).name(), f.getName());
            }
            if (values != null) {
                try {
                    DefaultMutableTreeNode tempNode;
                    tempNode = new TooltipTreeNode(name + ": " + f.get(values), tooltip);

                    node.add(tempNode);
                } catch (IllegalAccessException e) {
                    Logger.Error(e);
                }
            } else {
                TooltipTreeNode tempNode = new TooltipTreeNode(name, tooltip);
                node.add(tempNode);
            }
        }

        root.add(node);
    }

    private Integer getInt(String message, Integer defaultValue) {
        SpinnerNumberModel model = new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setValue(defaultValue);
        JOptionPane.showMessageDialog(tabItemPanel, spinner, message, JOptionPane.QUESTION_MESSAGE);
        return (int) spinner.getValue();
    }

    private Double getDouble(String message, double defaultValue) {
        SpinnerNumberModel model = new SpinnerNumberModel(defaultValue, null, null, 0.1);
        JSpinner spinner = new JSpinner(model);
        JOptionPane.showMessageDialog(tabItemPanel, spinner, message, JOptionPane.QUESTION_MESSAGE);
        int roundFactor = 1000; // Have to have this because floating
        return (double) Math.round(((Double) spinner.getValue()) * roundFactor) / roundFactor;
    }

    private Integer getOption(String message, Object[] options) {
        JComboBox<? extends Object> comboBox = new JComboBox<>(options);
        JOptionPane.showMessageDialog(tabItemPanel, comboBox, message, JOptionPane.QUESTION_MESSAGE);
        return comboBox.getSelectedIndex();
    }

    private Boolean getBool(String message) {
        Object[] options = {
                "True",
                "False"
        };
        int n = JOptionPane.showOptionDialog(tabItemPanel, message, message, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        return n == 0;
    }

    private String getString(String message) {
        JTextField field = new JTextField();
        JOptionPane.showMessageDialog(tabItemPanel, field, message, JOptionPane.QUESTION_MESSAGE);
        return field.getText();
    }

    private <T, X> Map<T, X> getMap(Map<T, X> starting) {
        return new HashMap<>();
    }

    private <T> List<T> getList(List<String> starting) {
        ListEditor<String> listEditor = new ListEditor<>(String.class, this::ValueSelector);
        JOptionPane.showConfirmDialog(tabItemPanel, listEditor.getMapEditorPanel(), "List", JOptionPane.OK_OPTION);
        return (List<T>) listEditor.getItems();
    }

    private Object getObject(Object o) {
        Gson g = new Gson();
        String data = g.toJson(o);
        JTextArea field = new JTextArea();
        field.setRows(10);
        field.setText(data);
        JOptionPane.showMessageDialog(tabItemPanel, field, "Editing JSON for object", JOptionPane.QUESTION_MESSAGE);
        return g.fromJson(field.getText(), o.getClass());
    }

    private <T> T ValueSelector(Class<T> clazz, String message, Object starting) {
        if (clazz == double.class) {
            return (T) getDouble(message, (double) starting);
        } else if (clazz == boolean.class) {
            return (T) getBool(message);
        } else if (clazz.isEnum()) {
            return (T) Enum.valueOf((Class<Enum>) clazz, clazz.getEnumConstants()[getOption(message, clazz.getEnumConstants())].toString());
        } else if (clazz == int.class) {
            return (T) getInt(message, (Integer) starting);
        } else if (clazz == String.class) {
            return (T) getString(message);
        } else if (clazz.isAssignableFrom(List.class)) {
            return (T) getList((List<String>) starting);
        }
        //else if (clazz.isAssignableFrom(Map.class)){
        //    return (T) getMap((Map) starting);
        //}
        else {
            Logger.Log("Cannot find setter for value, so defaulting to JSON editing");
            return (T) getObject(starting);
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        tabItemPanel = new JPanel();
        tabItemPanel.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabItemPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        this.$$$loadLabelText$$$(label1, this.$$$getMessageFromBundle$$$("IDEUI", "itemNameLabel"));
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        itemName = new JTextField();
        panel1.add(itemName, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabItemPanel.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        this.$$$loadLabelText$$$(label2, this.$$$getMessageFromBundle$$$("IDEUI", "itemIDLabel"));
        panel2.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        itemID = new JTextField();
        panel2.add(itemID, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabItemPanel.add(panel3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        this.$$$loadLabelText$$$(label3, this.$$$getMessageFromBundle$$$("IDEUI", "itemTextureLabel"));
        panel3.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel3.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        itemTexture = new JComboBox();
        panel3.add(itemTexture, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabItemPanel.add(panel4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        this.$$$loadLabelText$$$(label4, this.$$$getMessageFromBundle$$$("IDEUI", "itemStackSizeLabel"));
        panel4.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel4.add(spacer4, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        itemStackSize = new JSpinner();
        panel4.add(itemStackSize, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabItemPanel.add(panel5, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        notAddedTree = new JTree();
        notAddedTree.setRootVisible(false);
        panel5.add(notAddedTree, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        addedTree = new JTree();
        addedTree.setRootVisible(false);
        panel5.add(addedTree, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
    }

    private static Method $$$cachedGetBundleMethod$$$ = null;

    private String $$$getMessageFromBundle$$$(String path, String key) {
        ResourceBundle bundle;
        try {
            Class<?> thisClass = this.getClass();
            if ($$$cachedGetBundleMethod$$$ == null) {
                Class<?> dynamicBundleClass = thisClass.getClassLoader().loadClass("com.intellij.DynamicBundle");
                $$$cachedGetBundleMethod$$$ = dynamicBundleClass.getMethod("getBundle", String.class, Class.class);
            }
            bundle = (ResourceBundle) $$$cachedGetBundleMethod$$$.invoke(null, path, thisClass);
        } catch (Exception e) {
            bundle = ResourceBundle.getBundle(path);
        }
        return bundle.getString(key);
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadLabelText$$$(JLabel component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setDisplayedMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return tabItemPanel;
    }

}
