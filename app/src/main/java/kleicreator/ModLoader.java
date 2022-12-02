package kleicreator;

import kleicreator.data.Mod;
import kleicreator.editor.frames.EditorMain;
import kleicreator.sdk.internal.PluginHandler;
import kleicreator.savesystem.SaveSystem;
import kleicreator.util.Logger;

import javax.swing.*;

public class ModLoader {
    public static JFrame modEditorFrame;

    public static void LoadMod(String path) {
        Mod.path = path;
        SaveSystem.Load(path);
        CreateModEditorFrame();
        PluginHandler.TriggerEvent("OnModLoad");
        Logger.Debug("Loaded mod");
    }

    public static void CreateMod(String path, String author, String name) {
        Mod.path = path;
        CreateModEditorFrame();
        Mod.modName = name;
        Mod.modAuthor = author;
        Mod.modDescription = "Example Description";
        Mod.modVersion = new Mod.Version(1, 0, 0);

        SaveSystem.Save(path);
        Logger.Debug("Created mod");
    }

    public static void CreateModEditorFrame() {
        modEditorFrame = new JFrame();
        EditorMain editorMain = new EditorMain(modEditorFrame);
    }

    public static void ShowWarning(String message) {
        JOptionPane.showMessageDialog(modEditorFrame, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }


}
