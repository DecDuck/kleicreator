package kleicreator.modloader;

import com.google.gson.Gson;
import kleicreator.editor.frames.EditorMain;
import kleicreator.frames.ListEditor;
import kleicreator.frames.ModEditor;
import kleicreator.sdk.item.Item;
import kleicreator.modloader.classes.ResourceSpeech;
import kleicreator.modloader.resources.Resource;
import kleicreator.modloader.resources.ResourceManager;
import kleicreator.plugin.PluginHandler;
import kleicreator.savesystem.SaveSystem;
import kleicreator.sdk.logging.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModLoader {
    public static JFrame modEditorFrame;
    public static ModEditor modEditor;
    public static DefaultTableModel resourceModel;
    public static DefaultTableModel speechModel;
    public static DefaultTableModel recipeModel;

    public static String GetFileName(String fname) {
        int pos = fname.lastIndexOf(File.separator);
        if (pos > -1)
            return fname.substring(pos + 1);
        else
            return fname;
    }

    public static void LoadMod(String path) {
        Mod.path = path;
        SaveSystem.Load(path);
        CreateModEditorFrame();
        PluginHandler.TriggerEvent("OnModLoad");
        Update();
        Logger.Debug("Loaded mod");
    }

    public static void CreateMod(String path, String author, String name) {
        Mod.path = path;
        CreateModEditorFrame();
        Mod.modName = name;
        Mod.modAuthor = author;
        Mod.modDescription = "Example Description";
        Mod.modVersion = new Mod.Version(1, 0, 0);

        SaveSystem.Save(path);
        Update();
        Logger.Debug("Created mod");
    }

    public static void Update() {
        /*
        ResourceManager.GenerateResourceLists();
        int selectedModItem = modEditor.getModItemSelect().getSelectedIndex();
        int selectedModIcon = Mod.modIcon;
        int selectedModTexture = 0;
        try {
            selectedModTexture = ResourceManager.resources.indexOf(Mod.items.get(selectedModItem).itemTexture);
        } catch (IndexOutOfBoundsException e) {

        }
        modEditor.getModItemSelect().removeAllItems();
        for (Item i : Mod.items) {
            modEditor.getModItemSelect().addItem(i.itemName);
        }

        if (Mod.items.size() < selectedModItem) {
            selectedModItem = 0;
        }
        modEditor.getModItemSelect().setSelectedIndex(selectedModItem);

        resourceModel.setRowCount(0);

        modEditor.getModIconTextureSelect().removeAllItems();
        for (Resource r : ResourceManager.resources) {
            if (r.Is(ResourceTexture.class)) {
                ResourceTexture m = r.Get();
                resourceModel.addRow(new Object[]{ModLoader.GetFileName(m.texPath), "Texture", m.texPath + ";" + m.xmlPath, m.filePath});
            } else if (r.Is(ResourceSpeech.class)) {
                ResourceSpeech m = r.Get();
                resourceModel.addRow(new Object[]{m.speechFile.resourceName, "Speech", m.speechFile.filePath, "Size: " + m.speechFile.speech.size()});
            } else if (r.Is(ResourceAnimation.class)) {
                ResourceAnimation m = r.Get();
                resourceModel.addRow(new Object[]{GetFileName(m.animFilePath), "Animation", m.animFilePath, ""});
            }
        }

        for (Resource r : ResourceManager.inventoryimages) {
            modEditor.getModItemTextureSelect().addItem(GetFileName(((ResourceTexture) r).texPath));
        }
        for (Resource r : ResourceManager.modicons) {
            modEditor.getModIconTextureSelect().addItem(GetFileName(((ResourceTexture) r).texPath));
        }

        if (Mod.items.size() < selectedModIcon) {
            selectedModIcon = 0;
        }
        if (Mod.items.size() < selectedModTexture) {
            selectedModTexture = 0;
        }
        try {
            modEditor.getModItemTextureSelect().setSelectedIndex(selectedModTexture);
        } catch (IllegalArgumentException e) {

        }
        try {
            modEditor.getModIconTextureSelect().setSelectedIndex(selectedModIcon);
        } catch (IllegalArgumentException e) {

        }

        modEditor.getModNameTextField().setText(Mod.modName);
        modEditor.getModAuthorTextField().setText(Mod.modAuthor);
        modEditor.getModDescriptTextArea().setText(Mod.modDescription);
        modEditor.getModVersionTextField().setText(Mod.modVersion);

        RecipeLoader.Update();

        if (modEditor.getModItemSelect().getSelectedIndex() != -1 && modEditor.getModItemSelect().getSelectedIndex() < Mod.items.size() + 1) {
            modEditor.getModItemConfigPanel().setVisible(true);
            Item item = Mod.items.get(modEditor.getModItemSelect().getSelectedIndex());

            ItemLoader.UpdateTrees(item);

            modEditor.getModItemNameTextField().setText(item.itemName);
            modEditor.getModItemIdTextField().setText(item.itemId);
            if (item.itemTexture != -1) {
                modEditor.getModItemTextureSelect().setSelectedIndex(item.itemTexture);
            }
            modEditor.getModItemStackSize().setValue(item.stackSize);
        } else {
            modEditor.getModItemConfigPanel().setVisible(false);
        }

        PluginHandler.TriggerEvent("OnModEditorUpdate");

        modEditorFrame.validate();
        modEditorFrame.setVisible(true);
         */
    }

    public static Item SelectFromAllItems() {
        List<Item> items = Mod.GetAllItems();
        Object[] objArray = items.toArray();
        Object selection = JOptionPane.showInputDialog(modEditorFrame, "Select New Item", "Select New Item", JOptionPane.QUESTION_MESSAGE, null, objArray, 0);
        for(Item i : items){
            if(i.itemName.equals(selection.toString())){
                return i;
            }
        }
        return items.get(0);
    }

    public static void ReloadSpeech() {
        for (int i = 0; i < speechModel.getRowCount(); i++) {
            speechModel.removeRow(i);
        }

        for (Resource r : ResourceManager.resources) {
            if (r.Is(ResourceSpeech.class)) {
                ResourceSpeech m = r.Get();
                ResourceManager.LoadSpeech(m);
                speechModel.addRow(new String[]{
                        m.speechFile.resourceName,
                        m.speechFile.filePath,
                        "Speech",
                        String.valueOf(m.speechFile.speech.size())
                });
            }
        }
    }

    public static void SaveModConfig() {
        try {
            Mod.modName = modEditor.getModNameTextField().getText();
            Mod.modAuthor = modEditor.getModAuthorTextField().getText();
            Mod.modDescription = modEditor.getModDescriptTextArea().getText();
            Mod.modVersion = new Mod.Version(0, 0, 0);
            Mod.modIcon = modEditor.getModIconTextureSelect().getSelectedIndex();
        } catch (IndexOutOfBoundsException e) {
            Logger.Error(e);
            ShowWarning("There was a problem with saving the mod config. Please fix any errors and try again.");
        }

    }

    public static void SaveAll() {
        try {
            SaveModConfig();
            SaveSystem.Save(Mod.path);
        } catch (Exception e) {
            Logger.Error(e);
            ShowWarning("There was a problem with saving. Check the logs for more information");
        }
    }

    public static void CreateModEditorFrame() {
        /*
        speechModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        resourceModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        modEditor.getResourcesTable().setModel(resourceModel);
        modEditor.getModSpeechTable().setModel(speechModel);

        ((DefaultTreeModel) modEditor.getModItemComponetsAdded().getModel()).setRoot(new DefaultMutableTreeNode("Added"));
        ((DefaultTreeModel) modEditor.getModItemComponentNotAdded().getModel()).setRoot(new DefaultMutableTreeNode("Not Added"));

        ((DefaultTreeModel) modEditor.getModRecipesEditor().getModel()).setRoot(new DefaultMutableTreeNode("Recipe"));

        JTree addedTree = modEditor.getModItemComponetsAdded();
        JTree notAddedTree = modEditor.getModItemComponentNotAdded();

        JTree recipeTree = modEditor.getModRecipesEditor();

        RecipeLoader.SetupRecipeEditor(recipeTree);

        resourceModel.addColumn("Name");
        resourceModel.addColumn("Type");
        resourceModel.addColumn("Path");
        resourceModel.addColumn("Other");

        speechModel.addColumn("Name");
        speechModel.addColumn("Location");
        speechModel.addColumn("Type");
        speechModel.addColumn("Entries");

        ModLoaderActions.SetupListeners();

        // Redirect log output
        PrintStream out = System.out;
        System.setOut(new ModLoaderLogStreamer(out));

        modEditor.getModConfig().setSelectedIndex(1);

        modEditorFrame.pack();
        modEditorFrame.setLocationRelativeTo(null);
        Logger.Log("Successfully completed ModEditor setup.");
         */
        modEditorFrame = new JFrame();
        EditorMain editorMain = new EditorMain(modEditorFrame);
    }

    public static void ShowWarning(String message) {
        JOptionPane.showMessageDialog(modEditorFrame, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }


}
