package kleicreator.editor.tabs;

import kleicreator.editor.frames.EditorMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Tab extends JPanel {
    public String title;

    public Tab(){
        setLayout(new GridLayout(0, 1));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    public JPanel BuildTabComponent(EditorMain editorMain){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setOpaque(false);
        panel.add(new JLabel(title));

        JButton closeButton = new JButton();
        Tab tab = this;
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                editorMain.tabs.remove(tab);
                editorMain.UpdateTabs();
            }
        });
        closeButton.setIcon(new ImageIcon(ClassLoader.getSystemResource("icons/close.png")));
        closeButton.setOpaque(false);
        panel.add(closeButton);
        return panel;
    }
}
