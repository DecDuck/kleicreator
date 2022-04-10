package frames;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

public class ExportWindow {
    private JPanel exportWindowFrame;
    private JProgressBar exportProgressBar;
    private JLabel exportingLabel;

    public JPanel getExportWindowFrame() {
        return exportWindowFrame;
    }

    public JProgressBar getExportProgressBar() {
        return exportProgressBar;
    }

    public JLabel getExportingLabel() {
        return exportingLabel;
    }

}
