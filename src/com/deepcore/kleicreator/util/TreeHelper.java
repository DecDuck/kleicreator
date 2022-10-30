package com.deepcore.kleicreator.util;

import com.deepcore.kleicreator.logging.Logger;
import com.deepcore.kleicreator.modloader.ModLoader;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.lang.reflect.Field;

public class TreeHelper extends ModLoader {

    public static DefaultMutableTreeNode CreateNode(String name) {
        return new DefaultMutableTreeNode(name);
    }

    public static <T> void AddClassToTree(DefaultMutableTreeNode root, Class toAdd, T values) {
        Field[] fields = toAdd.getFields();
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(toAdd.getSimpleName());

        for (Field f : fields) {
            if (values != null) {
                try {
                    DefaultMutableTreeNode tempNode = new DefaultMutableTreeNode(f.getName() + ": " + f.get(values));
                    node.add(tempNode);
                } catch (IllegalAccessException e) {
                    Logger.Error(ExceptionUtils.getStackTrace(e));
                }
            } else {
                DefaultMutableTreeNode tempNode = new DefaultMutableTreeNode(f.getName());
                node.add(tempNode);
            }
        }

        root.add(node);
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
