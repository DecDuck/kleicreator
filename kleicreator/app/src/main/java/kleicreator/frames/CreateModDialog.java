package kleicreator.frames;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        newModConfigPanel = new JPanel();
        newModConfigPanel.setLayout(new GridLayoutManager(3, 3, new Insets(15, 15, 15, 15), -1, -1));
        nameLabel = new JLabel();
        nameLabel.setText("Mod Name:");
        newModConfigPanel.add(nameLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modNameTextField = new JTextField();
        newModConfigPanel.add(modNameTextField, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        modAuthorLabel = new JLabel();
        modAuthorLabel.setText("Mod Author");
        newModConfigPanel.add(modAuthorLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modAuthorTextField = new JTextField();
        newModConfigPanel.add(modAuthorTextField, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        cancel = new JButton();
        cancel.setText("Cancel");
        newModConfigPanel.add(cancel, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        createMod = new JButton();
        createMod.setText("Create");
        newModConfigPanel.add(createMod, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return newModConfigPanel;
    }
}
