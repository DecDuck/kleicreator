package kleicreator.editor.state;

import kleicreator.editor.nodes.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class ProjectExplorerState {
    public DefaultTreeModel projectExplorerModel;
    public Node root = new Node("Project");
    public NodeProject projectDetails = new NodeProject("Details");
    public NodeItemControl items = new NodeItemControl("Items");
    public NodeRecipeControl recipes = new NodeRecipeControl("Recipes");
    public NodeResourceControl resources = new NodeResourceControl("Resources");

    public ProjectExplorerState(){
        root.add(projectDetails);
        root.add(items);
        root.add(recipes);
        root.add(resources);
        projectExplorerModel = new DefaultTreeModel(root);
    }
}
