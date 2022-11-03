package kleicreator.editor.listeners;

import kleicreator.editor.frames.EditorMain;
import kleicreator.editor.nodes.*;
import kleicreator.editor.state.ProjectExplorerState;
import kleicreator.editor.tabs.*;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProjectExplorerMouseListener extends MouseAdapter {
    private JTree tree;
    private ProjectExplorerState state;
    private EditorMain editorMain;

    public ProjectExplorerMouseListener(JTree tree, ProjectExplorerState state, EditorMain editorMain) {
        this.tree = tree;
        this.state = state;
        this.editorMain = editorMain;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() != 2){
            return;
        }
        TreePath clickPath = tree.getPathForLocation(e.getX(), e.getY());
        Object node = clickPath.getLastPathComponent();
        if(node == null){
            return;
        }
        if(node instanceof NodeProject){
            UniqueAddTab(new TabProject());
        }
        if(node instanceof NodeItemControl){
            UniqueAddTab(new TabItemControl());
        }
        if(node instanceof NodeRecipeControl){
            UniqueAddTab(new TabRecipeControl());
        }
        if(node instanceof NodeResourceControl){
            UniqueAddTab(new TabResourceControl());
        }
        if(node instanceof NodeItem){
            UniqueContentAddTab(new TabItem(((NodeItem) node).item));
        }
        if(node instanceof NodeRecipe){
            UniqueContentAddTab(new TabRecipe(((NodeRecipe) node).recipe));
        }
        if(node instanceof NodeResource){
            UniqueContentAddTab(new TabResource(((NodeResource) node).resource));
        }
        editorMain.UpdateTabs();
    }

    private void UniqueAddTab(Tab tab){
        for(Tab t : Tab.tabs){
            if(t.getClass().equals(tab.getClass())){
                return;
            }
        }
        Tab.tabs.add(tab);
    }

    private void UniqueContentAddTab(Tab tab){
        for(Tab t : Tab.tabs){
            if(t.equals(tab)){
                return;
            }
        }
        Tab.tabs.add(tab);
    }
}
