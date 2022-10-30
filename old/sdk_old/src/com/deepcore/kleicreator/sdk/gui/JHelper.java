package com.deepcore.kleicreator.sdk.gui;

import com.deepcore.kleicreator.modloader.ModLoader;

import javax.swing.*;
import java.awt.*;

public class JHelper {

    public static JLabel CreateTitleJLabel(String title){
        JLabel t = new JLabel(title);
        t.setFont(t.getFont().deriveFont(Font.BOLD).deriveFont(18f));
        return t;
    }

}
