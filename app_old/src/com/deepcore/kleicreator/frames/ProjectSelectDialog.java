package com.deepcore.kleicreator.frames;

import javax.swing.*;

public class ProjectSelectDialog {
    private JPanel ProjectSelectPanel;
    private JTable projectsListTable;
    private JButton newMod;
    private JButton loadMod;
    private JLabel titleLabel;
    private JScrollPane tableScrollPlane;
    private JButton configButton;

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

    public JButton getConfigButton() {
        return configButton;
    }
}
