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
        if(clickPath == null){
            return;
        }
        Object node = clickPath.getLastPathComponent();
        Tab toFocus = null;
        if(node == null){
            return;
        }
        if(node instanceof NodeProject){
            toFocus = UniqueAddTab(new TabProject());
        }
        if(node instanceof NodeItemControl){
            toFocus = UniqueAddTab(new TabItemControl(editorMain));
        }
        if(node instanceof NodeRecipeControl){
            toFocus = UniqueAddTab(new TabRecipeControl());
        }
        if(node instanceof NodeResourceControl){
            toFocus = UniqueAddTab(new TabResourceControl());
        }
        if(node instanceof NodeItem){
            toFocus = UniqueContentAddTab(new TabItem(((NodeItem) node).item));
        }
        if(node instanceof NodeRecipe){
            toFocus = UniqueContentAddTab(new TabRecipe(((NodeRecipe) node).recipe));
        }
        if(node instanceof NodeResource){
            toFocus = UniqueContentAddTab(new TabResource(((NodeResource) node).resource));
        }
        editorMain.UpdateTabs();
        if(toFocus != null){
            editorMain.SetTabFocus(toFocus);
        }
    }

    private Tab UniqueAddTab(Tab tab){
        for(Tab t : Tab.tabs){
            if(t.getClass().equals(tab.getClass())){
                return t;
            }
        }
        Tab.tabs.add(tab);
        return tab;
    }

    private Tab UniqueContentAddTab(Tab tab){
        for(Tab t : Tab.tabs){
            if(t.equals(tab)){
                return t;
            }
        }
        Tab.tabs.add(tab);
        return tab;
    }
}
