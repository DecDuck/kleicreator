package modloader;

import config.GlobalConfig;
import frames.ModEditor;
import items.ItemLoader;
import logging.Logger;
import items.Item;
import master.Master;
import modloader.resources.Resource;
import modloader.resources.ResourceManager;
import org.apache.commons.lang3.exception.ExceptionUtils;
import recipes.RecipeLoader;
import resources.ResourceLoader;
import savesystem.SaveObject;
import savesystem.SaveSystem;
import speech.SpeechFile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.*;
import java.awt.event.*;
import java.io.*;

public class ModLoader {
    public static JFrame modEditorFrame;
    public static ModEditor modEditor;
    public static DefaultTableModel resourceModel;
    public static DefaultTableModel speechModel;
    public static DefaultTableModel recipeModel;

    public static String fileComponent(String fname) {
        int pos = fname.lastIndexOf(File.separator);
        if(pos > -1)
            return fname.substring(pos + 1);
        else
            return fname;
    }

    public static void LoadMod(String path){
        Mod.path = path;
        SaveSystem.Load(path);
        Debug();
        CreateModEditorFrame();
        Update();
        Logger.Log("Loaded mod");
    }
    public static void CreateMod(String path, String author, String name){
        Mod.path = path;
        CreateModEditorFrame();
        Mod.modName = name;
        Mod.modAuthor = author;
        Mod.modDescription = "Example Description";
        Mod.modVersion = "1.0";

        SaveSystem.Save(path);
        Update();
        Logger.Log("Created mod");
    }

    public static void Debug(){
        //Nothing here
    }

    public static void Update(){
        ResourceManager.GenerateResourceLists();
        int selectedModItem = modEditor.getModItemSelect().getSelectedIndex();
        int selectedModIcon = Mod.modIcon;
        int selectedModTexture = 0;
        try{
            selectedModTexture = ResourceManager.resources.indexOf(Mod.items.get(selectedModItem).itemTexture);
        }catch(IndexOutOfBoundsException e){

        }

        modEditor.getModItemSelect().removeAllItems();
        for(int i = 0; i < Mod.items.size(); i++){
            modEditor.getModItemSelect().addItem(Mod.items.get(i).itemName);
        }

        if(Mod.items.size() < selectedModItem){
            selectedModItem = 0;
        }
        modEditor.getModItemSelect().setSelectedIndex(selectedModItem);

        resourceModel.setRowCount(0);

        modEditor.getModIconTextureSelect().removeAllItems();
        modEditor.getModItemTextureSelect().removeAllItems();
        for(Resource r: ResourceManager.resources){
            if(r.isTexture){
                resourceModel.addRow(new Object[] { ModLoader.fileComponent(r.texture.texPath), "Texture", r.texture.texPath + ";" + r.texture.xmlPath, r.filePath});
            }else if(r.isSpeech){
                resourceModel.addRow(new Object[] { r.speechFile.resourceName, "Speech", r.speechFile.filePath, "Size: " + r.speechFile.speech.size()});
            }else if(r.isAnim){
                resourceModel.addRow(new Object[] { fileComponent(r.animFilePath), "Animation", r.animFilePath, ""});
            }
        }

        for(Resource r: ResourceManager.inventoryimages){
            modEditor.getModItemTextureSelect().addItem(fileComponent(r.texture.texPath));
        }
        for(Resource r: ResourceManager.modicons){
            modEditor.getModIconTextureSelect().addItem(fileComponent(r.texture.texPath));
        }

        if(Mod.items.size() < selectedModIcon){
            selectedModIcon = 0;
        }
        if(Mod.items.size() < selectedModTexture){
            selectedModTexture = 0;
        }
        try{
            modEditor.getModItemTextureSelect().setSelectedIndex(selectedModTexture);
        }catch (IllegalArgumentException e){

        }
        try{
            modEditor.getModIconTextureSelect().setSelectedIndex(selectedModIcon);
        }catch (IllegalArgumentException e){

        }

        modEditor.getModNameTextField().setText(Mod.modName);
        modEditor.getModAuthorTextField().setText(Mod.modAuthor);
        modEditor.getModDescriptTextArea().setText(Mod.modDescription);
        modEditor.getModVersionTextField().setText(Mod.modVersion);

        RecipeLoader.Update();

        if(modEditor.getModItemSelect().getSelectedIndex() != -1 && modEditor.getModItemSelect().getSelectedIndex() < Mod.items.size() + 1){
            modEditor.getModItemConfigPanel().setVisible(true);
            Item item = Mod.items.get(modEditor.getModItemSelect().getSelectedIndex());

            ItemLoader.UpdateTrees(item);

            modEditor.getModItemNameTextField().setText(item.itemName);
            modEditor.getModItemIdTextField().setText(item.itemId);
            if(item.itemTexture != -1){
                modEditor.getModItemTextureSelect().setSelectedIndex(item.itemTexture);
            }
            modEditor.getModItemStackSize().setValue(item.stackSize);
        }else{
            modEditor.getModItemConfigPanel().setVisible(false);
        }
        modEditorFrame.validate();
    }

    public static void ReloadSpeech(){
        for(int i = 0; i < speechModel.getRowCount(); i++){
            speechModel.removeRow(i);
        }

        for(Resource r: ResourceManager.resources){
            if(r.isSpeech){
                ResourceManager.ReloadSpeechResource(r);
                speechModel.addRow(new Object[]{r.speechFile.resourceName, r.speechFile.filePath, "Speech", r.speechFile.speech.size()});
            }
        }
    }

    public static void SaveItem(){
        try {
            Item item = null;
            try{
                item = Mod.items.get(modEditor.getModItemSelect().getSelectedIndex());
            }catch (Exception e){
                return;
            }

            item.itemName = modEditor.getModItemNameTextField().getText();
            item.itemId = modEditor.getModItemIdTextField().getText();
            item.itemTexture = modEditor.getModItemTextureSelect().getSelectedIndex();
            item.stackSize = (int) modEditor.getModItemStackSize().getValue();
        }catch(java.lang.IndexOutOfBoundsException e){
            Logger.Error(ExceptionUtils.getStackTrace(e));
            ShowWarning("There was a problem with saving the item. Please fix any errors and try again.");
        }
    }

    public static void SaveModConfig(){
        try{
            Mod.modName = modEditor.getModNameTextField().getText();
            Mod.modAuthor = modEditor.getModAuthorTextField().getText();
            Mod.modDescription = modEditor.getModDescriptTextArea().getText();
            Mod.modVersion = modEditor.getModVersionTextField().getText();
            Mod.modIcon = modEditor.getModIconTextureSelect().getSelectedIndex();
        }catch (java.lang.IndexOutOfBoundsException e){
            Logger.Error(ExceptionUtils.getStackTrace(e));
            ShowWarning("There was a problem with saving the mod config. Please fix any errors and try again.");
        }

    }

    public static void SaveAll(){
        try{
            SaveItem();
            SaveModConfig();
            SaveSystem.Save(Mod.path);
        }catch(Exception e){
            Logger.Error(ExceptionUtils.getStackTrace(e));
            ShowWarning("There was a problem with saving.");
        }
    }

    public static void CreateModEditorFrame(){
        modEditorFrame = new JFrame(String.format("KleiCreator %s | %s", Master.version, Mod.modName));
        ImageIcon img = new ImageIcon(ResourceLoader.class.getResource("dstguimodcreatorlogo.png"));
        modEditorFrame.setIconImage(img.getImage());
        modEditor = new ModEditor();

        modEditorFrame.setContentPane(modEditor.getModEditorPanel());
        modEditorFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        modEditorFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                if(new SaveObject().hashCode() != SaveSystem.TempLoad(Mod.path).hashCode()){
                    if(GlobalConfig.askSaveOnLeave && getBool("Save?")){
                        SaveAll();
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

        speechModel = new DefaultTableModel(){
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

        ((DefaultTreeModel)modEditor.getModItemComponetsAdded().getModel()).setRoot(new DefaultMutableTreeNode("Added"));
        ((DefaultTreeModel)modEditor.getModItemComponentNotAdded().getModel()).setRoot(new DefaultMutableTreeNode("Not Added"));

        ((DefaultTreeModel)modEditor.getModRecipesEditor().getModel()).setRoot(new DefaultMutableTreeNode("Recipe"));

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

        modEditorFrame.pack();
        modEditorFrame.setLocationRelativeTo(null);
        modEditorFrame.setVisible(true);
        Logger.Log("Successfully completed ModEditor setup.");
    }

    public static int getInt(String message){
        SpinnerNumberModel model = new SpinnerNumberModel(0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1);
        JSpinner spinner = new JSpinner(model);
        JOptionPane.showMessageDialog(modEditorFrame, spinner, message, JOptionPane.QUESTION_MESSAGE);
        return (int)spinner.getValue();
    }

    public static double getFloat(String message){
        SpinnerNumberModel model = new SpinnerNumberModel(0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0.1);
        JSpinner spinner = new JSpinner(model);
        JOptionPane.showMessageDialog(modEditorFrame, spinner, message, JOptionPane.QUESTION_MESSAGE);
        return (double) spinner.getValue();
    }

    public static int getOption(String message, Object[] options){
        JComboBox comboBox = new JComboBox(options);
        JOptionPane.showMessageDialog(modEditorFrame, comboBox, message, JOptionPane.QUESTION_MESSAGE);
        return comboBox.getSelectedIndex();
    }

    public static boolean getBool(String message){
        Object[] options = {
            "Yes",
            "No"
        };
        int n = JOptionPane.showOptionDialog(modEditorFrame, message, message, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        return n == 0;
    }

    public static String getString(String message){
        JTextField field = new JTextField();
        JOptionPane.showMessageDialog(modEditorFrame, field, message, JOptionPane.QUESTION_MESSAGE);
        return field.getText();
    }

    public static void ShowWarning(String message){
        JOptionPane.showMessageDialog(modEditorFrame, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
}
