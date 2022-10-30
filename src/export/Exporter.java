package export;

import constants.Constants;
import export.templates.Template;
import frames.ExportDialog;
import logging.Logger;
import modloader.Mod;
import modloader.ModLoader;
import modloader.resources.Resource;
import modloader.resources.ResourceManager;
import org.apache.commons.lang3.exception.ExceptionUtils;
import resources.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;

public class Exporter {
    private static ExportDialog exportDialog;
    private static JFrame exportWindowFrame;
    private static int points;

    public static void Export(){
        try{
            InitLoading();
            String modOutput = Constants.FILE_LOCATION + "/exported/" + Mod.escapedModName() + "/";
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

            for(int i = 0; i < Mod.items.size(); i++){
                Templates.itemTemplates.get(i).Create();
                Write(Templates.itemTemplates.get(i), modOutput + "scripts/prefabs/" + Mod.items.get(i).itemId + ".lua");
                MoveLoading();
            }

            Done(modOutput);
        }catch(Exception e){
            Logger.Error(ExceptionUtils.getStackTrace(e));
            ModLoader.ShowWarning("There was an error while exporting the mod");
        }

    }
    private static void CreateFolders(String outputLocation){
        new File(Constants.FILE_LOCATION + "/exported/").mkdir();
        new File(outputLocation).mkdir();
        new File(outputLocation + "images").mkdir();
        new File(outputLocation + "images/inventoryimages").mkdir();
        new File(outputLocation + "images/bigportraits").mkdir();
        new File(outputLocation + "scripts").mkdir();
        new File(outputLocation + "scripts/prefabs").mkdir();
        new File(outputLocation + "anim").mkdir();
    }
    private static void CopyResources(String outputLocation){
        Logger.Log("Starting resource copy");
        for(Resource r: ResourceManager.resources){
            if(r.isTexture) {
                try {
                    Files.copy(Paths.get(r.texture.texPath), Paths.get(outputLocation + r.filePath + ModLoader.fileComponent(r.texture.texPath)), StandardCopyOption.REPLACE_EXISTING);
                    Files.copy(Paths.get(r.texture.xmlPath), Paths.get(outputLocation + r.filePath + ModLoader.fileComponent(r.texture.xmlPath)), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    Logger.Error(ExceptionUtils.getStackTrace(e));
                }
            }
            if(r.isAnim){
                try {
                    Files.copy(Paths.get(r.animFilePath), Paths.get(outputLocation + "/anim/" + ModLoader.fileComponent(r.animFilePath)), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    Logger.Error(ExceptionUtils.getStackTrace(e));
                }
            }
        }
        Logger.Log("Finished resource copy");
    }
    private static void InitLoading(){
        exportDialog = new ExportDialog();
        exportWindowFrame = new JFrame("Exporting...");
        Logger.Log("Exporting...");
        Logger.Log("Starting exporting init");
        ImageIcon img = new ImageIcon(ResourceLoader.class.getResource("dstguimodcreatorlogo.png"));
        exportWindowFrame.setIconImage(img.getImage());
        exportWindowFrame.setContentPane(exportDialog.getExportWindowFrame());
        exportWindowFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        exportWindowFrame.pack();
        exportWindowFrame.setLocationRelativeTo(null);
        exportWindowFrame.setVisible(true);
        points = 6 + Mod.items.size();
        Logger.Log("Finished Init");
    }
    private static void Write(Template toWrite, String fileLocation){
        Logger.Log("Writing to " + fileLocation);
        try {
            new File(fileLocation).createNewFile();
            FileWriter f = new FileWriter(fileLocation, false);
            f.write(toWrite.getTemplate());
            f.close();
        } catch (IOException e) {
            Logger.Error(ExceptionUtils.getStackTrace(e));
        }
        Logger.Log("Done");
    }
    private static void MoveLoading(){
        int currentValue = exportDialog.getExportProgressBar().getValue();
        float eachPointValue = 100/points;
        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            
        }
        exportDialog.getExportProgressBar().setValue(currentValue + (int) eachPointValue);
        exportWindowFrame.pack();
        SwingUtilities.updateComponentTreeUI(exportWindowFrame);
    }
    private static void Done(String finishedLocation){
        JOptionPane.showMessageDialog(ModLoader.modEditorFrame, "Done!");
        exportDialog.getExportProgressBar().setValue(100);
        exportWindowFrame.dispose();
        try {
            Desktop.getDesktop().open(new File(finishedLocation));
        } catch (IOException e) {
            Logger.Error(ExceptionUtils.getStackTrace(e));
        }
        Logger.Log("Finished export");
    }
}
