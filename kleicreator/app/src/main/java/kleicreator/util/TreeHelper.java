package kleicreator.util;

import kleicreator.items.ItemLoader;
import kleicreator.modloader.ModLoader;
import kleicreator.sdk.item.FieldData;
import kleicreator.sdk.logging.Logger;
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
        TooltipTreeNode node = new TooltipTreeNode(toAdd.getSimpleName(), "");

        for (Field f : fields) {
            String name = f.getName();
            String tooltip = "";
            if(f.isAnnotationPresent(FieldData.class)){
                name = f.getAnnotation(FieldData.class).name();
                tooltip = f.getAnnotation(FieldData.class).tooltip();
                ItemLoader.annotatedFieldMap.put(f.getAnnotation(FieldData.class).name(), f.getName());
            }
            if (values != null) {
                try {
                    DefaultMutableTreeNode tempNode;
                    tempNode = new TooltipTreeNode(name + ": " + f.get(values), tooltip);

                    node.add(tempNode);
                } catch (IllegalAccessException e) {
                    Logger.Error(ExceptionUtils.getStackTrace(e));
                }
            } else {
                TooltipTreeNode tempNode = new TooltipTreeNode(name, tooltip);
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
