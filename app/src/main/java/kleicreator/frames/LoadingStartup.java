package kleicreator.frames;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import kleicreator.master.Master;

import javax.swing.*;
import java.awt.*;

public class LoadingStartup {
    private JPanel loadingBar;
    private JProgressBar progressBar;

    private JFrame frame;

    public LoadingStartup() {
        frame = new JFrame("KleiCreator | Loading...");
        frame.setContentPane(loadingBar);
        frame.setUndecorated(true);
        frame.setIconImage(Master.icon.getImage());
        frame.setType(Window.Type.UTILITY);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void SetProgress(int value, String text) {
        progressBar.setValue(value);
        progressBar.setString(text);
        frame.pack();
    }

    public void Destroy() {
        frame.dispose();
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
        loadingBar = new JPanel();
        loadingBar.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setIcon(new ImageIcon(getClass().getResource("/kleicreator_startup_splash.png")));
        label1.setText("");
        loadingBar.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        loadingBar.add(progressBar, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return loadingBar;
    }
}
