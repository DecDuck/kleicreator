package com.deepcore.kleicreator.sdk.gui;

import com.deepcore.kleicreator.modloader.ModLoader;

import javax.swing.*;

import static com.deepcore.kleicreator.modloader.ModLoader.*;

public class ModEditor {
    public static void AddTab(String title, JPanel panel){
        ModLoader.modEditor.getModConfig().addTab(title, panel);
    }

    public static void Update(){
        ModLoader.Update();
    }

    public static void ShowMessage(String title, String message){
        JOptionPane.showMessageDialog(ModLoader.modEditorFrame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void ShowWarning(String title, String message){
        JOptionPane.showMessageDialog(ModLoader.modEditorFrame, message, title, JOptionPane.WARNING_MESSAGE);
    }

    public static  <T> T GetValueFromUser(Class<T> clazz, String message){
        if(clazz == double.class){
            return (T) getFloat(message);
        }
        if(clazz == boolean.class){
            return (T) getBool(message);
        }
        if(clazz.isEnum()){
            return (T) Enum.valueOf((Class<Enum>) clazz, clazz.getEnumConstants()[getOption(message,clazz.getEnumConstants())].toString());
        }
        return null;
    }
}
