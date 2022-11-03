package kleicreator.editor.tabs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Tab extends JPanel {
    public String title;

    public static List<Tab> tabs = new ArrayList<>();

    public Tab(){
        setLayout(new GridLayout(0, 1));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }
}
