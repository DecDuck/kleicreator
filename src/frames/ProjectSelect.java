package frames;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

public class ProjectSelect {
    private JPanel ProjectSelectPanel;
    private JTable projectsListTable;
    private JButton newMod;
    private JButton loadMod;
    private JLabel titleLabel;
    private JScrollPane tableScrollPlane;

    public JPanel getProjectSelectPanel() {
        return ProjectSelectPanel;
    }

    public JTable getProjectsListTable() {
        return projectsListTable;
    }

    public JButton getNewMod() {
        return newMod;
    }

    public JButton getLoadMod() {
        return loadMod;
    }

    public JLabel getTitleLabel() {
        return titleLabel;
    }

    public JScrollPane getTableScrollPlane() {
        return tableScrollPlane;
    }

}
