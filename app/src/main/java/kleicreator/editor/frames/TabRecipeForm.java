package kleicreator.editor.frames;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import kleicreator.items.AllItems;
import kleicreator.data.Mod;
import kleicreator.recipes.Recipe;
import kleicreator.items.Item;
import kleicreator.util.TreeHelper;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

public class TabRecipeForm {
    private JPanel tabRecipePanel;
    private JTree recipeTree;
    private Recipe recipe;

    private DefaultMutableTreeNode ingredientsNode;
    private DefaultMutableTreeNode techNode;
    private DefaultMutableTreeNode tabNode;

    public TabRecipeForm(JPanel tab, Recipe recipe) {
        this.recipe = recipe;
        tab.add(tabRecipePanel);

        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selRow = recipeTree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = recipeTree.getPathForLocation(e.getX(), e.getY());
                if (selRow != -1) {
                    if (e.getClickCount() == 1) {

                    } else if (e.getClickCount() == 2) {
                        String selected = selPath.getLastPathComponent().toString();
                        String recipeId = selPath.getPathComponent(0).toString();
                        if (selected == recipeId) {
                            //We're changing this recipe
                            Item newResult = AllItems.SelectFromAllItems(tab);
                            recipe.result = newResult.itemId;
                        } else if (selected.startsWith("Tech: ")) {
                            //We're changing the tech
                            Object[] options = getNames(Recipe.TECH.class);
                            Object selectionObject = JOptionPane.showInputDialog(tab, "New Tech: ", "Select Tech", JOptionPane.QUESTION_MESSAGE, null, options, options[recipe.tech.ordinal()]);
                            String selectionString = selectionObject.toString();
                            recipe.tech = Recipe.TECH.valueOf(selectionString);
                        } else if (selected.startsWith("Tab: ")) {
                            //We're changing the tab
                            Object[] options = getNames(Recipe.RECIPETAB.class);
                            Object selectionObject = JOptionPane.showInputDialog(tab, "New Tab: ", "Select Tab", JOptionPane.QUESTION_MESSAGE, null, options, options[recipe.tab.ordinal()]);
                            String selectionString = selectionObject.toString();
                            recipe.tab = Recipe.RECIPETAB.valueOf(selectionString);
                        } else if (selected.startsWith("Add")) {
                            //We're adding an ingredient
                            Item newIngredient = AllItems.SelectFromAllItems(tab);
                            recipe.ingredients.add(newIngredient.itemId);
                        } else if (selPath.getPathComponent(1).toString() == "Ingredients" && selected != "Add") {
                            //We're deleting an ingredient
                            //Maybe add an edit element later (Issue #4)
                            recipe.ingredients.remove(Mod.GetItemByName(selected).itemId);
                        }
                        UpdateTree();
                    }
                }
            }
        };
        recipeTree.addMouseListener(ml);

        DefaultTreeModel model = (DefaultTreeModel) recipeTree.getModel();
        model.setRoot(new DefaultMutableTreeNode(Mod.GetItemById(recipe.result).itemName));
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

        ingredientsNode = TreeHelper.CreateNode("Ingredients");
        root.add(ingredientsNode);

        techNode = TreeHelper.CreateNode("");
        tabNode = TreeHelper.CreateNode("");
        root.add(techNode);
        root.add(tabNode);

        UpdateTree();
    }

    public String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }

    public void UpdateTree() {
        DefaultTreeModel model = (DefaultTreeModel) recipeTree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        root.setUserObject(Mod.GetItemById(recipe.result).itemName);

        ingredientsNode.removeAllChildren();
        for (int i = 0; i < recipe.ingredients.size(); i++) {
            ingredientsNode.add(TreeHelper.CreateNode(Mod.GetItemById(recipe.ingredients.get(i)).itemName));
        }
        ingredientsNode.add(TreeHelper.CreateNode("Add"));

        techNode.setUserObject("Tech: " + recipe.tech.toString());
        tabNode.setUserObject("Tab: " + recipe.tab.toString());

        model.reload();
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
        tabRecipePanel = new JPanel();
        tabRecipePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        recipeTree = new JTree();
        tabRecipePanel.add(recipeTree, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return tabRecipePanel;
    }

}
