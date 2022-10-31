package kleicreator.recipes;

import kleicreator.sdk.item.Item;
import kleicreator.sdk.logging.Logger;
import kleicreator.util.TreeHelper;
import kleicreator.modloader.Mod;
import kleicreator.modloader.ModLoader;

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

    public static void SetupRecipeEditor(final JTree tree) {
        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                ModLoader.SaveItem();
                int selRow = tree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
                if (selRow != -1) {
                    if (e.getClickCount() == 1) {

                    } else if (e.getClickCount() == 2) {
                        String selected = selPath.getLastPathComponent().toString();
                        String recipeId = selPath.getPathComponent(0).toString();
                        int i = modEditor.getModRecipesSelector().getSelectedIndex();
                        if (selected == recipeId) {
                            //We're changing this recipe
                            Item newResult = ModLoader.SelectFromAllItems();
                            Mod.recipes.get(i).result = newResult.itemId;
                            Update();
                            return;
                        }
                        if (selected.startsWith("Tech: ")) {
                            //We're changing the tech
                            Object[] options = getNames(Recipe.TECH.class);
                            Object selectionObject = JOptionPane.showInputDialog(modEditorFrame, "New Tech: ", "Select Tech", JOptionPane.QUESTION_MESSAGE, null, options, options[Mod.recipes.get(i).tech.ordinal()]);
                            String selectionString = selectionObject.toString();
                            Mod.recipes.get(i).tech = Recipe.TECH.valueOf(selectionString);
                            Update();
                            return;
                        }
                        if (selected.startsWith("Tab: ")) {
                            //We're changing the tab
                            Object[] options = getNames(Recipe.RECIPETAB.class);
                            Object selectionObject = JOptionPane.showInputDialog(modEditorFrame, "New Tab: ", "Select Tab", JOptionPane.QUESTION_MESSAGE, null, options, options[Mod.recipes.get(i).tab.ordinal()]);
                            String selectionString = selectionObject.toString();
                            Mod.recipes.get(i).tab = Recipe.RECIPETAB.valueOf(selectionString);
                            Update();
                            return;
                        }
                        if (selected.startsWith("Add")) {
                            //We're adding an ingredient
                            Item newIngredient = ModLoader.SelectFromAllItems();
                            Mod.recipes.get(i).ingredients.add(newIngredient.itemId);
                            Update();
                            return;
                        }
                        if (selPath.getPathComponent(1).toString() == "Ingredients" && selected != "Add") {
                            //We're deleting an ingredient
                            //Maybe add an edit element later (Issue #4)
                            Mod.recipes.get(i).ingredients.remove(Mod.GetItemByName(selected).itemId);
                            Update();
                            return;
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

    public static void Update() {

        JComboBox recipeSelection = modEditor.getModRecipesSelector();
        int savedIndex = recipeSelection.getSelectedIndex();
        DefaultComboBoxModel model = (DefaultComboBoxModel) recipeSelection.getModel();
        model.removeAllElements();
        for (int i = 0; i < Mod.recipes.size(); i++) {
            model.addElement(Mod.GetItemById(Mod.recipes.get(i).result).itemName + " (" +Mod.recipes.get(i).id+")");
        }
        try {
            recipeSelection.setSelectedIndex(savedIndex);
        } catch (Exception e) {
            Logger.Warn("recipeSelection.setSelectedIndex failed, probably due to the recipe being deleted");
        }

        int recipeIndex = modEditor.getModRecipesSelector().getSelectedIndex();
        if (recipeIndex == -1) {
            modEditor.getModRecipesEditor().setVisible(false);
        } else {
            modEditor.getModRecipesEditor().setVisible(true);
            if (Mod.recipes.size() - 1 < recipeIndex) {
                Logger.Error("Selection too high");
            }
            LoadRecipe(Mod.recipes.get(recipeIndex));
        }

    }

    public static void LoadRecipe(Recipe r) {
        JTree tree = modEditor.getModRecipesEditor();
        DefaultTreeModel model = (DefaultTreeModel) modEditor.getModRecipesEditor().getModel();
        String expansionState = TreeHelper.getExpansionState(tree);
        model.setRoot(new DefaultMutableTreeNode(Mod.GetItemById(r.result).itemName));
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

        DefaultMutableTreeNode ingredients = TreeHelper.CreateNode("Ingredients");
        for (int i = 0; i < r.ingredients.size(); i++) {
            ingredients.add(TreeHelper.CreateNode(Mod.GetItemById(r.ingredients.get(i)).itemName));
        }
        ingredients.add(TreeHelper.CreateNode("Add"));

        root.add(ingredients);
        root.add(TreeHelper.CreateNode("Tech: " + r.tech.toString()));
        root.add(TreeHelper.CreateNode("Tab: " + r.tab.toString()));

        model.reload();

        TreeHelper.setExpansionState(expansionState, tree);
    }

    public static void CreateNewRecipe() {
        Recipe r = new Recipe();
        r.result = "id";
        r.ingredients = new ArrayList<String>();
        r.tab = Recipe.RECIPETAB.TOOLS;
        r.tech = Recipe.TECH.NONE;
        Mod.recipes.add(r);
        Update();
        modEditor.getModRecipesSelector().setSelectedIndex(Mod.recipes.size()-1);
        Logger.Debug("Created recipe");
    }

    public static void DeleteRecipe() {
        int recipeIndex = modEditor.getModRecipesSelector().getSelectedIndex();
        if (recipeIndex != -1) {
            Mod.recipes.remove(recipeIndex);
        }
        Update();
        Logger.Debug("Deleted recipe");
    }

}
