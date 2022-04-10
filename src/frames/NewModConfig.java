package frames;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

public class NewModConfig {
    private JPanel newModConfigPanel;
    private JTextField modNameTextField;
    private JLabel nameLabel;
    private JTextField modAuthorTextField;
    private JLabel modAuthorLabel;
    private JButton createMod;
    private JButton cancel;

    public JButton getCancel() {
        return cancel;
    }

    public JPanel getNewModConfigPanel() {
        return newModConfigPanel;
    }

    public JTextField getModNameTextField() {
        return modNameTextField;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public JTextField getModAuthorTextField() {
        return modAuthorTextField;
    }

    public JLabel getModAuthorLabel() {
        return modAuthorLabel;
    }

    public JButton getCreateMod() {
        return createMod;
    }

}
