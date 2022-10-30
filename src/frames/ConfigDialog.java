package frames;

import javax.swing.*;

public class ConfigDialog {
    private JCheckBox darkModeCheckBox;
    private JCheckBox askSaveOnLeaveCheckBox;
    private JPanel frame;
    private JButton save;

    public JCheckBox getDarkModeCheckBox() {
        return darkModeCheckBox;
    }

    public JCheckBox getAskSaveOnLeaveCheckBox() {
        return askSaveOnLeaveCheckBox;
    }

    public JPanel getFrame() {
        return frame;
    }

    public JButton getSave() {
        return save;
    }
}
