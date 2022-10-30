package frames;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

public class SpeechConfig {
    private JPanel speechConfigPanel;
    private JButton speechCreate;
    private JTextField speechNameTextField;
    private JLabel speechNameLabel;

    public JPanel getSpeechConfigPanel() {
        return speechConfigPanel;
    }

    public JButton getSpeechCreate() {
        return speechCreate;
    }

    public JTextField getSpeechNameTextField() {
        return speechNameTextField;
    }

    public JLabel getSpeechNameLabel() {
        return speechNameLabel;
    }

}
