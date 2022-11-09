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

    }

}
