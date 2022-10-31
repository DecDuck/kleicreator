package kleicreator.frames;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

public class ModEditor {
    private JPanel modEditorPanel;
    private JTabbedPane modConfig;
    private JPanel mainConfig;
    private JPanel modItems;
    private JPanel modCharacters;
    private JPanel resources;
    private JTextField modNameTextField;
    private JTextField modAuthorTextField;
    private JTextArea modDescriptTextArea;
    private JTextField modVersionTextField;
    private JLabel modNameLabel;
    private JLabel modAuthorLabel;
    private JLabel modDescriptionLabel;
    private JLabel modVersionLabel;
    private JPanel modItemConfigPanel;
    private JButton modItemDelete;
    private JButton modItemCreate;
    private JLabel modItemNameLabel;
    private JTextField modItemNameTextField;
    private JComboBox modItemTextureSelect;
    private JLabel modItemTextureSelectLabel;
    private JPanel modSpeechConfig;
    private JLabel modItemIdLabel;
    private JTextField modItemIdTextField;
    private JComboBox modItemSelect;
    private JButton modItemSave;
    private JButton modConfigSave;
    private JTable resourcesTable;
    private JButton resourcesAdd;
    private JButton resourcesRemove;
    private JScrollPane resourcesScroll;
    private JButton saveAll;
    private JButton modExport;
    private JComboBox modIconTextureSelect;
    private JLabel modIconLabel;
    private JTable modSpeechTable;
    private JScrollPane modSpeechTableScrollPlane;
    private JButton modSpeechReloadSpeech;
    private JButton openFolder;
    private JPanel modExportPanel;
    private JTree modItemComponentNotAdded;
    private JTree modItemComponetsAdded;
    private JButton githubButton;
    private JButton wikiButton;
    private JPanel modRecipes;
    private JPanel modRecipesButtonPanel;
    private JTree modRecipesEditor;
    private JComboBox modRecipesSelector;
    private JButton modRecipesCreate;
    private JButton modRecipesDelete;
    private JSpinner modItemStackSize;
    private JPanel aboutTab;
    private JLabel logOutput;
    private JToolBar toolbar;
    private JButton toolbarSave;
    private JLabel title;
    private JPanel modRecipesPanel;

    public JPanel getAboutTab() {
        return aboutTab;
    }

    public JToolBar getToolbar() {
        return toolbar;
    }

    public JButton getToolbarSave() {
        return toolbarSave;
    }

    public JLabel getTitle() {
        return title;
    }

    public JPanel getModRecipes() {
        return modRecipes;
    }

    public JPanel getModRecipesButtonPanel() {
        return modRecipesButtonPanel;
    }

    public JButton getGithubButton() {
        return githubButton;
    }

    public JButton getWikiButton() {
        return wikiButton;
    }

    public JPanel getModEditorPanel() {
        return modEditorPanel;
    }

    public JTabbedPane getModConfig() {
        return modConfig;
    }

    public JPanel getMainConfig() {
        return mainConfig;
    }

    public JPanel getModItems() {
        return modItems;
    }

    public JPanel getModCharacters() {
        return modCharacters;
    }

    public JPanel getResources() {
        return resources;
    }

    public JTextField getModNameTextField() {
        return modNameTextField;
    }

    public JTextField getModAuthorTextField() {
        return modAuthorTextField;
    }

    public JTextArea getModDescriptTextArea() {
        return modDescriptTextArea;
    }

    public JTextField getModVersionTextField() {
        return modVersionTextField;
    }

    public JLabel getModNameLabel() {
        return modNameLabel;
    }

    public JLabel getModAuthorLabel() {
        return modAuthorLabel;
    }

    public JLabel getModDescriptionLabel() {
        return modDescriptionLabel;
    }

    public JLabel getModVersionLabel() {
        return modVersionLabel;
    }

    public JPanel getModItemConfigPanel() {
        return modItemConfigPanel;
    }

    public JButton getModItemDelete() {
        return modItemDelete;
    }

    public JButton getModItemCreate() {
        return modItemCreate;
    }

    public JLabel getModItemNameLabel() {
        return modItemNameLabel;
    }

    public JTextField getModItemNameTextField() {
        return modItemNameTextField;
    }

    public JComboBox getModItemTextureSelect() {
        return modItemTextureSelect;
    }

    public JLabel getModItemTextureSelectLabel() {
        return modItemTextureSelectLabel;
    }

    public JPanel getModSpeechConfig() {
        return modSpeechConfig;
    }

    public JLabel getModItemIdLabel() {
        return modItemIdLabel;
    }

    public JTextField getModItemIdTextField() {
        return modItemIdTextField;
    }

    public JComboBox getModItemSelect() {
        return modItemSelect;
    }

    public JButton getModItemSave() {
        return modItemSave;
    }

    public JButton getModConfigSave() {
        return modConfigSave;
    }

    public JTable getResourcesTable() {
        return resourcesTable;
    }

    public JButton getResourcesAdd() {
        return resourcesAdd;
    }

    public JButton getResourcesRemove() {
        return resourcesRemove;
    }

    public JScrollPane getResourcesScroll() {
        return resourcesScroll;
    }

    public JButton getSaveAll() {
        return saveAll;
    }

    public JButton getModExport() {
        return modExport;
    }

    public JComboBox getModIconTextureSelect() {
        return modIconTextureSelect;
    }

    public JLabel getModIconLabel() {
        return modIconLabel;
    }

    public JTable getModSpeechTable() {
        return modSpeechTable;
    }

    public JScrollPane getModSpeechTableScrollPlane() {
        return modSpeechTableScrollPlane;
    }

    public JButton getModSpeechReloadSpeech() {
        return modSpeechReloadSpeech;
    }

    public JButton getOpenFolder() {
        return openFolder;
    }

    public JPanel getModExportPanel() {
        return modExportPanel;
    }

    public JTree getModItemComponentNotAdded() {
        return modItemComponentNotAdded;
    }

    public JTree getModItemComponetsAdded() {
        return modItemComponetsAdded;
    }

    public JPanel getModRecipesPanel() {
        return modRecipesPanel;
    }

    public JTree getModRecipesEditor() {
        return modRecipesEditor;
    }

    public JComboBox getModRecipesSelector() {
        return modRecipesSelector;
    }

    public JButton getModRecipesCreate() {
        return modRecipesCreate;
    }

    public JButton getModRecipesDelete() {
        return modRecipesDelete;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public JLabel getLogOutput() {
        return logOutput;
    }

    public JSpinner getModItemStackSize() {
        return modItemStackSize;
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
        modEditorPanel = new JPanel();
        modEditorPanel.setLayout(new GridBagLayout());
        modConfig = new JTabbedPane();
        modConfig.setEnabled(true);
        Font modConfigFont = this.$$$getFont$$$(null, -1, -1, modConfig.getFont());
        if (modConfigFont != null) modConfig.setFont(modConfigFont);
        modConfig.setTabLayoutPolicy(1);
        modConfig.setTabPlacement(2);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 100.0;
        gbc.weighty = 100.0;
        gbc.fill = GridBagConstraints.BOTH;
        modEditorPanel.add(modConfig, gbc);
        aboutTab = new JPanel();
        aboutTab.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        modConfig.addTab("", new ImageIcon(getClass().getResource("/kleicreator_info_tab.png")), aboutTab);
        modConfig.setDisabledIconAt(0, new ImageIcon(getClass().getResource("/kleicreator_info_tab.png")));
        modConfig.setEnabledAt(0, false);
        mainConfig = new JPanel();
        mainConfig.setLayout(new GridLayoutManager(9, 3, new Insets(20, 20, 20, 20), -1, -1));
        Font mainConfigFont = this.$$$getFont$$$(null, -1, -1, mainConfig.getFont());
        if (mainConfigFont != null) mainConfig.setFont(mainConfigFont);
        mainConfig.setToolTipText("Configure things like mod name, mod author and description.");
        modConfig.addTab("Mod", null, mainConfig, "Change things such as mod name, author, config options");
        modNameLabel = new JLabel();
        modNameLabel.setText("Mod Name");
        mainConfig.add(modNameLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modAuthorLabel = new JLabel();
        modAuthorLabel.setText("Mod Author");
        mainConfig.add(modAuthorLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modAuthorTextField = new JTextField();
        mainConfig.add(modAuthorTextField, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        modDescriptionLabel = new JLabel();
        modDescriptionLabel.setText("Mod Description");
        mainConfig.add(modDescriptionLabel, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modVersionLabel = new JLabel();
        modVersionLabel.setText("Mod Version");
        mainConfig.add(modVersionLabel, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modVersionTextField = new JTextField();
        mainConfig.add(modVersionTextField, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        modConfigSave = new JButton();
        modConfigSave.setText("Save");
        mainConfig.add(modConfigSave, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modIconTextureSelect = new JComboBox();
        mainConfig.add(modIconTextureSelect, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modIconLabel = new JLabel();
        modIconLabel.setText("Mod Icon");
        mainConfig.add(modIconLabel, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modDescriptTextArea = new JTextArea();
        modDescriptTextArea.setText("");
        mainConfig.add(modDescriptTextArea, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, -1), null, 0, false));
        modNameTextField = new JTextField();
        mainConfig.add(modNameTextField, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        modItems = new JPanel();
        modItems.setLayout(new GridLayoutManager(4, 2, new Insets(20, 20, 20, 20), -1, -1));
        modConfig.addTab("Items", null, modItems, "Create, delete and modify your modded items");
        modItemConfigPanel = new JPanel();
        modItemConfigPanel.setLayout(new GridLayoutManager(7, 2, new Insets(0, 0, 0, 0), -1, -1));
        modItems.add(modItemConfigPanel, new GridConstraints(0, 1, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(1000, -1), new Dimension(1000, -1), null, 0, false));
        final Spacer spacer1 = new Spacer();
        modItemConfigPanel.add(spacer1, new GridConstraints(6, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        modItemNameLabel = new JLabel();
        modItemNameLabel.setText("Item Name:");
        modItemConfigPanel.add(modItemNameLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modItemNameTextField = new JTextField();
        modItemConfigPanel.add(modItemNameTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        modItemTextureSelectLabel = new JLabel();
        modItemTextureSelectLabel.setText("Texture:");
        modItemConfigPanel.add(modItemTextureSelectLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        modItemConfigPanel.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        modItemIdLabel = new JLabel();
        modItemIdLabel.setText("Item ID:");
        modItemConfigPanel.add(modItemIdLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modItemIdTextField = new JTextField();
        modItemConfigPanel.add(modItemIdTextField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        modItemTextureSelect = new JComboBox();
        modItemConfigPanel.add(modItemTextureSelect, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modItemComponentNotAdded = new JTree();
        modItemComponentNotAdded.setShowsRootHandles(false);
        modItemConfigPanel.add(modItemComponentNotAdded, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        modItemComponetsAdded = new JTree();
        modItemComponetsAdded.setShowsRootHandles(false);
        modItemConfigPanel.add(modItemComponetsAdded, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        modItemStackSize = new JSpinner();
        modItemConfigPanel.add(modItemStackSize, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Stack Size:");
        modItemConfigPanel.add(label1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modItemCreate = new JButton();
        modItemCreate.setText("New Item");
        modItems.add(modItemCreate, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(200, -1), 0, false));
        modItemDelete = new JButton();
        modItemDelete.setText("Delete Item");
        modItems.add(modItemDelete, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(200, -1), 0, false));
        modItemSelect = new JComboBox();
        modItemSelect.setEditable(false);
        modItems.add(modItemSelect, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, -1), null, null, 0, false));
        modItemSave = new JButton();
        modItemSave.setText("Save");
        modItems.add(modItemSave, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(200, -1), 0, false));
        modRecipes = new JPanel();
        modRecipes.setLayout(new GridLayoutManager(1, 2, new Insets(20, 20, 20, 20), -1, -1));
        modConfig.addTab("Recipes", null, modRecipes, "Add/Remove custom recipes");
        modRecipesButtonPanel = new JPanel();
        modRecipesButtonPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        modRecipes.add(modRecipesButtonPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        modRecipesSelector = new JComboBox();
        modRecipesButtonPanel.add(modRecipesSelector, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, -1), null, null, 0, false));
        modRecipesCreate = new JButton();
        modRecipesCreate.setText("Create");
        modRecipesButtonPanel.add(modRecipesCreate, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modRecipesDelete = new JButton();
        modRecipesDelete.setText("Delete");
        modRecipesButtonPanel.add(modRecipesDelete, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modRecipesEditor = new JTree();
        modRecipes.add(modRecipesEditor, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        modCharacters = new JPanel();
        modCharacters.setLayout(new GridLayoutManager(1, 1, new Insets(20, 20, 20, 20), -1, -1));
        modConfig.addTab("Characters", null, modCharacters, "Create, delete and modify your modded characters");
        modConfig.setEnabledAt(4, false);
        resources = new JPanel();
        resources.setLayout(new GridLayoutManager(3, 1, new Insets(20, 20, 20, 20), -1, -1));
        modConfig.addTab("Resources", null, resources, "Import assets to use in your mod");
        resourcesScroll = new JScrollPane();
        resources.add(resourcesScroll, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        resourcesTable = new JTable();
        resourcesScroll.setViewportView(resourcesTable);
        resourcesAdd = new JButton();
        resourcesAdd.setText("Add Resource");
        resources.add(resourcesAdd, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        resourcesRemove = new JButton();
        resourcesRemove.setText("Remove Resource");
        resources.add(resourcesRemove, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modSpeechConfig = new JPanel();
        modSpeechConfig.setLayout(new GridLayoutManager(3, 2, new Insets(20, 20, 20, 20), -1, -1));
        modConfig.addTab("Speech", modSpeechConfig);
        modSpeechTableScrollPlane = new JScrollPane();
        modSpeechConfig.add(modSpeechTableScrollPlane, new GridConstraints(0, 1, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        modSpeechTable = new JTable();
        modSpeechTableScrollPlane.setViewportView(modSpeechTable);
        modSpeechReloadSpeech = new JButton();
        modSpeechReloadSpeech.setText("Reload");
        modSpeechConfig.add(modSpeechReloadSpeech, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(300, -1), 0, false));
        openFolder = new JButton();
        openFolder.setText("Open Folder");
        modSpeechConfig.add(openFolder, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(300, -1), 0, false));
        final JToolBar toolBar1 = new JToolBar();
        toolBar1.setFloatable(false);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        modEditorPanel.add(toolBar1, gbc);
        modExport = new JButton();
        modExport.setText("Export");
        toolBar1.add(modExport);
        saveAll = new JButton();
        saveAll.setText("Save Project");
        toolBar1.add(saveAll);
        logOutput = new JLabel();
        Font logOutputFont = this.$$$getFont$$$("Liberation Mono", -1, -1, logOutput.getFont());
        if (logOutputFont != null) logOutput.setFont(logOutputFont);
        logOutput.setText("Log Output");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        modEditorPanel.add(logOutput, gbc);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return modEditorPanel;
    }

}
