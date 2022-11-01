package kleicreator.export;


import kleicreator.frames.LoadingStartup;
import kleicreator.master.Master;
import kleicreator.plugin.PluginHandler;
import kleicreator.sdk.constants.Constants;
import kleicreator.export.templates.Template;
import kleicreator.frames.ExportDialog;
import kleicreator.modloader.Mod;
import kleicreator.modloader.ModLoader;
import kleicreator.modloader.classes.ResourceAnimation;
import kleicreator.modloader.classes.ResourceTexture;
import kleicreator.modloader.resources.Resource;
import kleicreator.modloader.resources.ResourceManager;
import kleicreator.sdk.logging.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Exporter {
    private static LoadingStartup exportDialog;
    private static JFrame exportWindowFrame;
    private static int maxJobs;
    private static int jobs;

    public static void Export() {
        try {
            InitLoading();
            String modOutput = Constants.constants.FetchExportLocation(Mod.escapedModName());
            new File(modOutput).mkdir();
            ResourceManager.GenerateResourceLists(); //So we don't have to call it several times during exporting

            CreateFolders(modOutput);
            MoveLoading();

            CopyResources(modOutput);
            MoveLoading();

            TemplateLoader.LoadTemplates();
            MoveLoading();

            Templates.CreateTemplates();
            MoveLoading();

            Templates.modinfo.Create();
            Write(Templates.modinfo, modOutput + "modinfo.lua");
            MoveLoading();

            Templates.modmain.Create();
            Write(Templates.modmain, modOutput + "modmain.lua");
            MoveLoading();

            for (int i = 0; i < Mod.items.size(); i++) {
                Templates.itemTemplates.get(i).Create();
                Write(Templates.itemTemplates.get(i), modOutput + "scripts/prefabs/" + Mod.items.get(i).itemId + ".lua");
                MoveLoading();
            }

            PluginHandler.TriggerEvent("OnExport", modOutput);

            Done(modOutput);
        } catch (Exception e) {
            Logger.Error(e);
            ModLoader.ShowWarning("There was an error while exporting the mod");
        }

    }

    private static void CreateFolders(String outputLocation) {
        new File(Constants.constants.KLEICREATOR_LOCATION + "/exported/").mkdir();
        new File(outputLocation).mkdir();
        new File(outputLocation + "images").mkdir();
        new File(outputLocation + "images/inventoryimages").mkdir();
        new File(outputLocation + "images/bigportraits").mkdir();
        new File(outputLocation + "scripts").mkdir();
        new File(outputLocation + "scripts/prefabs").mkdir();
        new File(outputLocation + "anim").mkdir();
    }

    private static void CopyResources(String outputLocation) {
        Logger.Debug("Starting resource copy");
        for (Resource r : ResourceManager.resources) {
            if (r.Is(ResourceTexture.class)) {
                try {
                    ResourceTexture m = r.Get();
                    Files.copy(Paths.get(m.texPath), Paths.get(outputLocation + m.filePath + ModLoader.GetFileName(m.texPath)), StandardCopyOption.REPLACE_EXISTING);
                    Files.copy(Paths.get(m.xmlPath), Paths.get(outputLocation + m.filePath + ModLoader.GetFileName(m.xmlPath)), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    Logger.Error(e);
                }
            }
            if (r.Is(ResourceAnimation.class)) {
                try {
                    ResourceAnimation m = r.Get();
                    Files.copy(Paths.get(m.animFilePath), Paths.get(outputLocation + "/anim/" + ModLoader.GetFileName(m.animFilePath)), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    Logger.Error(e);
                }
            }
        }
        Logger.Debug("Finished resource copy");
    }

    private static void InitLoading() {
        exportDialog = new LoadingStartup();
        Logger.Debug("Exporting...");
        Logger.Debug("Starting exporting init");
        maxJobs = 6 + Mod.items.size();
        jobs = 0;
        Logger.Debug("Finished Init");
    }

    private static void Write(Template toWrite, String fileLocation) {
        Logger.Debug("Writing to " + fileLocation);
        try {
            new File(fileLocation).createNewFile();
            FileWriter f = new FileWriter(fileLocation, false);
            f.write(toWrite.getTemplate());
            f.close();
        } catch (IOException e) {
            Logger.Error(e);
        }
        Logger.Debug("Done");
    }

    private static void MoveLoading() {
        jobs++;
        exportDialog.SetProgress((int) (((float)jobs/maxJobs)*100), "Exporting...");
    }

    private static void Done(String finishedLocation) {
        Logger.Log("Finished export");
        exportDialog.SetProgress(100, "Done!");
        JOptionPane.showMessageDialog(ModLoader.modEditorFrame, "Successfully completed export!", "Export complete", JOptionPane.INFORMATION_MESSAGE);
        exportDialog.Destroy();
        try {
            Desktop.getDesktop().open(new File(finishedLocation));
        } catch (IOException e) {
            Logger.Error(e);
        }
    }
}
