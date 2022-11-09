package kleicreator.editor.listeners;

import kleicreator.modloader.Mod;
import kleicreator.savesystem.SaveObject;
import kleicreator.savesystem.SaveSystem;
import kleicreator.config.Config;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class EditorWindowListener implements WindowListener {
    private JFrame frame;
    public EditorWindowListener(JFrame frame){
        this.frame = frame;
    }
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        if(!new SaveObject().toString().equals(SaveSystem.TempLoad(Mod.path).toString())){
            if ((Boolean) Config.GetData("kleicreator.asksaveonleave")) {
                int option = JOptionPane.showConfirmDialog(frame, "Save project?", "Save?", JOptionPane.YES_NO_CANCEL_OPTION);
                if(option == 0){
                    // SaveAll();
                }
                if(option == 2){
                    return;
                }
            }
        }

        frame.dispose();
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
