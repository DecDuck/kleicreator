package frames;

import javax.swing.*;

public class ConfigDialog {
    private JCheckBox askSaveOnLeaveCheckBox;
    private JPanel frame;
    private JButton save;
    private JComboBox themeBox;

    public JCheckBox getAskSaveOnLeaveCheckBox() {
        return askSaveOnLeaveCheckBox;
    }

    public JPanel getFrame() {
        return frame;
    }

    public JButton getSave() {
        return save;
    }

    public JComboBox getThemeBox() {
        return themeBox;
    }
}
