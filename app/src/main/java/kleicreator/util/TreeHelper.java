package kleicreator.util;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class TreeHelper {

    public static DefaultMutableTreeNode CreateNode(String name) {
        return new DefaultMutableTreeNode(name);
    }

    public static String getExpansionState(JTree tree) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < tree.getRowCount(); i++) {
            TreePath tp = tree.getPathForRow(i);
            if (tree.isExpanded(i)) {
                sb.append(tp.toString());
                sb.append(",");
            }
        }

        return sb.toString();

    }

    public static void setExpansionState(String s, JTree tree) {

        for (int i = 0; i < tree.getRowCount(); i++) {
            TreePath tp = tree.getPathForRow(i);
            if (s.contains(tp.toString())) {
                tree.expandRow(i);
            }
        }
    }

}
