package kleicreator.frames;

import javax.swing.*;

public class CreateModDialog {
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
