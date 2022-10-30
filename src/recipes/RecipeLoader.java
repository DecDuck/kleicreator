package recipes;

import frames.ModEditor;
import items.Item;
import items.components.*;
import logging.Logger;
import modloader.Mod;
import modloader.ModLoader;
import util.TreeHelper;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

public class RecipeLoader extends ModLoader {

    public static Recipe currentlySelected;

    public static void SetupRecipeEditor(JTree tree){
        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                ModLoader.SaveItem();
                int selRow = tree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
                if(selRow != -1) {
                    if(e.getClickCount() == 1) {

                    }
                    else if(e.getClickCount() == 2) {
                        String selected = selPath.getLastPathComponent().toString();
                        String recipeId = selPath.getPathComponent(0).toString();
                        for(int i = 0; i < Mod.recipes.size(); i++){
                            if(recipeId == Mod.recipes.get(i).result){
                                if(selected == recipeId){
                                    //We're changing this recipe
                                    String newResult = getString("New result: ");
                                    Mod.recipes.get(i).result = newResult;
                                    Update();
                                    return;
                                }
                                if(selected.startsWith("Tech: ")){
                                    //We're changing the tech
                                    Object[] options = getNames(Recipe.TECH.class);
                                    Object selectionObject = JOptionPane.showInputDialog(modEditorFrame, "New Tech: ", "Select Tech", JOptionPane.QUESTION_MESSAGE, null, options, options[Mod.recipes.get(i).tech.ordinal()]);
                                    String selectionString = selectionObject.toString();
                                    Mod.recipes.get(i).tech = Recipe.TECH.valueOf(selectionString);
                                    Update();
                                    return;
                                }
                                if(selected.startsWith("Tab: ")){
                                    //We're changing the tab
                                    Object[] options = getNames(Recipe.RECIPETAB.class);
                                    Object selectionObject = JOptionPane.showInputDialog(modEditorFrame, "New Tab: ", "Select Tab", JOptionPane.QUESTION_MESSAGE, null, options, options[Mod.recipes.get(i).tech.ordinal()]);
                                    String selectionString = selectionObject.toString();
                                    Mod.recipes.get(i).tab = Recipe.RECIPETAB.valueOf(selectionString);
                                    Update();
                                    return;
                                }
                                if(selected.startsWith("Add")){
                                    //We're adding an ingredient
                                    String newIngredient = getString("New ingredient: ");
                                    Mod.recipes.get(i).ingredients.add(newIngredient);
                                    Update();
                                    return;
                                }
                                if(Mod.recipes.get(i).ingredients.contains(selected)){
                                    //We're deleting an ingredient
                                    //Maybe add an edit element later
                                    Mod.recipes.get(i).ingredients.remove(selected);
                                    Update();
                                    return;
                                }
                            }
                        }


                    }
                }
            }
        };
        tree.addMouseListener(ml);
    }

    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }

    public static void Update(){

        JComboBox recipeSelection = modEditor.getModRecipesSelector();
        int savedIndex = recipeSelection.getSelectedIndex();
        DefaultComboBoxModel model = (DefaultComboBoxModel) recipeSelection.getModel();
        model.removeAllElements();
        for(int i = 0; i < Mod.recipes.size(); i++){
            model.addElement(Mod.recipes.get(i).result);
        }
        try{
            recipeSelection.setSelectedIndex(savedIndex);
        }catch(Exception e){
            Logger.Warn("recipeSelection.setSelectedIndex failed, probably due to the recipe being deleted");
        }


        int recipeIndex = modEditor.getModRecipesSelector().getSelectedIndex();
        if(recipeIndex == -1){
            modEditor.getModRecipesEditor().setVisible(false);
        }else{
            modEditor.getModRecipesEditor().setVisible(true);
            if(Mod.recipes.size()-1 < recipeIndex){
                Logger.Error("Selection too long");
            }
            LoadRecipe(Mod.recipes.get(recipeIndex));
        }

    }

    public static void LoadRecipe(Recipe r){
        JTree tree = modEditor.getModRecipesEditor();
        DefaultTreeModel model = (DefaultTreeModel)modEditor.getModRecipesEditor().getModel();
        String expansionState = TreeHelper.getExpansionState(tree);
        model.setRoot(new DefaultMutableTreeNode(r.result));
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();

        DefaultMutableTreeNode ingredients = TreeHelper.CreateNode("Ingredients");
        for(int i = 0; i < r.ingredients.size(); i++){
            ingredients.add(TreeHelper.CreateNode(r.ingredients.get(i)));
        }
        ingredients.add(TreeHelper.CreateNode("Add"));

        root.add(ingredients);
        root.add(TreeHelper.CreateNode("Tech: " + r.tech.toString()));
        root.add(TreeHelper.CreateNode("Tab: " + r.tab.toString()));

        model.reload();

        TreeHelper.setExpansionState(expansionState, tree);
    }

    public static void CreateNewRecipe(){
        Logger.Log("Creating recipe");
        Recipe r = new Recipe();
        r.result = "id";
        r.ingredients = new ArrayList<String>();
        r.tab = Recipe.RECIPETAB.TOOLS;
        r.tech = Recipe.TECH.NONE;
        Mod.recipes.add(r);
        Update();
    }

    public static void DeleteRecipe(){
        int recipeIndex = modEditor.getModRecipesSelector().getSelectedIndex();
        if(recipeIndex != -1){
            Mod.recipes.remove(recipeIndex);
        }
        Update();
    }

}
