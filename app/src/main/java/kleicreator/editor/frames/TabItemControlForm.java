package kleicreator.editor.frames;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import kleicreator.editor.tabs.Tab;
import kleicreator.editor.tabs.TabItem;
import kleicreator.data.Mod;
import kleicreator.items.Item;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.ResourceBundle;

public class TabItemControlForm {

    private JPanel tabItemControlPanel;
    private JTable itemTable;
    private JButton add;
    private JButton remove;

    private final DefaultTableModel tableModel;

    public TabItemControlForm(JPanel tab, EditorMain editorMain) {
        tab.add(tabItemControlPanel);

        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        itemTable.setModel(tableModel);
        itemTable.setBorder(BorderFactory.createEmptyBorder());

        tableModel.addColumn("Name");
        tableModel.addColumn("ID");
        tableModel.addColumn("Stack Size");
        UpdateTable();

        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Item item = new Item();
                Mod.items.add(item);
                UpdateTable();
                editorMain.UpdateTabs();
                editorMain.UpdateProjectExplorer();
            }
        });

        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selectedIndex = itemTable.getSelectedRow();
                if (selectedIndex != -1) {
                    Item item = Mod.items.get(selectedIndex);
                    int option = JOptionPane.showConfirmDialog(tab, String.format("Delete '%s'?", item.itemName), "Delete item?", JOptionPane.OK_CANCEL_OPTION);
                    if (option == 0) {
                        for (Iterator<Tab> iterator = editorMain.tabs.iterator(); iterator.hasNext(); ) {
                            Tab tab = iterator.next();
                            if (tab instanceof TabItem) {
                                if (((TabItem) tab).item == item) {
                                    iterator.remove();
                                }
                            }
                        }
                        Mod.items.remove(selectedIndex);
                        UpdateTable();
                        editorMain.UpdateTabs();
                        editorMain.UpdateProjectExplorer();
                    }
                } else {
                    JOptionPane.showMessageDialog(tab, "No item selected", "No item select", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    public void UpdateTable() {
        tableModel.setRowCount(0); // Clear rows
        for (Item item : Mod.items) {
            tableModel.addRow(new String[]{item.itemName, item.itemId, String.valueOf(item.stackSize)});
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
        tabItemControlPanel = new JPanel();
        tabItemControlPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JScrollPane scrollPane1 = new JScrollPane();
        tabItemControlPanel.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        itemTable = new JTable();
        scrollPane1.setViewportView(itemTable);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabItemControlPanel.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        add = new JButton();
        this.$$$loadButtonText$$$(add, this.$$$getMessageFromBundle$$$("IDEUI", "itemControlNew"));
        panel1.add(add, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        remove = new JButton();
        this.$$$loadButtonText$$$(remove, this.$$$getMessageFromBundle$$$("IDEUI", "itemControlRemove"));
        panel1.add(remove, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
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
    private void $$$loadButtonText$$$(AbstractButton component, String text) {
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
            component.setMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return tabItemControlPanel;
    }

}
