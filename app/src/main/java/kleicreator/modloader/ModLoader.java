package kleicreator.modloader;

import com.google.gson.Gson;
import kleicreator.editor.frames.EditorMain;
import kleicreator.frames.ListEditor;
import kleicreator.frames.ModEditor;
import kleicreator.savesystem.SaveObject;
import kleicreator.sdk.item.Item;
import kleicreator.items.ItemLoader;
import kleicreator.master.Master;
import kleicreator.modloader.classes.ResourceAnimation;
import kleicreator.modloader.classes.ResourceSpeech;
import kleicreator.modloader.classes.ResourceTexture;
import kleicreator.modloader.resources.Resource;
import kleicreator.modloader.resources.ResourceManager;
import kleicreator.plugin.PluginHandler;
import kleicreator.recipes.RecipeLoader;
import kleicreator.savesystem.SaveSystem;
import kleicreator.sdk.config.Config;
import kleicreator.sdk.logging.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        String truncatedModName = Mod.modName;
        if (truncatedModName.length() > 20) {
            truncatedModName = truncatedModName.substring(0, 20);
            truncatedModName += "...";
        }
        modEditorFrame = new JFrame(String.format("KleiCreator %s | %s", Master.version, truncatedModName));
        modEditorFrame.setIconImage(Master.icon.getImage());
        modEditor = new ModEditor();

        modEditorFrame.setContentPane(modEditor.getModEditorPanel());
        modEditorFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        modEditorFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                // Logger.Log(SaveSystem.TempLoad(Mod.path).toString());
                // Logger.Log(new SaveObject().toString());
                if(!new SaveObject().toString().equals(SaveSystem.TempLoad(Mod.path).toString())){
                    if ((Boolean) Config.GetData("kleicreator.asksaveonleave")) {
                        int option = JOptionPane.showConfirmDialog(modEditorFrame, "Save project?", "Save?", JOptionPane.YES_NO_CANCEL_OPTION);
                        if(option == 0){
                            SaveAll();
                        }
                        if(option == 2){
                            return;
                        }
                    }
                }

                modEditorFrame.dispose();
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

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

        ItemLoader.SetupAddedTree(addedTree);
        ItemLoader.SetupNotAddedTree(notAddedTree);

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

    public static Integer getInt(String message, Integer defaultValue) {
        SpinnerNumberModel model = new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setValue(defaultValue);
        JOptionPane.showMessageDialog(modEditorFrame, spinner, message, JOptionPane.QUESTION_MESSAGE);
        return (int) spinner.getValue();
    }

    public static Double getDouble(String message, double defaultValue) {
        SpinnerNumberModel model = new SpinnerNumberModel(defaultValue, null, null, 0.1);
        JSpinner spinner = new JSpinner(model);
        JOptionPane.showMessageDialog(modEditorFrame, spinner, message, JOptionPane.QUESTION_MESSAGE);
        int roundFactor = 1000; // Have to have this because floating
        return (double) Math.round(((Double) spinner.getValue()) * roundFactor) / roundFactor;
    }

    public static Integer getOption(String message, Object[] options) {
        JComboBox<? extends Object> comboBox = new JComboBox<>(options);
        JOptionPane.showMessageDialog(modEditorFrame, comboBox, message, JOptionPane.QUESTION_MESSAGE);
        return comboBox.getSelectedIndex();
    }

    public static Boolean getBool(String message) {
        Object[] options = {
                "True",
                "False"
        };
        int n = JOptionPane.showOptionDialog(modEditorFrame, message, message, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        return n == 0;
    }

    public static String getString(String message) {
        JTextField field = new JTextField();
        JOptionPane.showMessageDialog(modEditorFrame, field, message, JOptionPane.QUESTION_MESSAGE);
        return field.getText();
    }

    public static <T, X> Map<T, X> getMap(Map<T, X> starting) {
        return new HashMap<>();
    }

    public static <T> List<T> getList(List<String> starting){
        ListEditor<String> listEditor = new ListEditor<>(String.class);
        JOptionPane.showConfirmDialog(modEditorFrame, listEditor.getMapEditorPanel(), "List", JOptionPane.OK_OPTION);
        return (List<T>) listEditor.getItems();
    }

    public static Object getObject(Object o) {
        Gson g = new Gson();
        String data = g.toJson(o);
        JTextArea field = new JTextArea();
        field.setRows(10);
        field.setText(data);
        JOptionPane.showMessageDialog(modEditorFrame, field, "Editing JSON for object", JOptionPane.QUESTION_MESSAGE);
        return g.fromJson(field.getText(), o.getClass());
    }

    public static <T> T getValueFromUser(Class<T> clazz, String message, Object starting) {
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
        } else if(clazz.isAssignableFrom(List.class)){
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

    public static void ShowWarning(String message) {
        JOptionPane.showMessageDialog(modEditorFrame, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }


}
