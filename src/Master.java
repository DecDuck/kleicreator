import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import config.Config;
import config.GlobalConfig;
import constants.Constants;
import logging.Logger;
import frames.*;
import modloader.*;
import resources.ResourceLoader;
import savesystem.*;
import updater.Updater;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static constants.Constants.FILE_LOCATION;

public class Master {
    public static ProjectSelect projectSelect;
    public static JFrame projectSelectFrame;

    public static float version = 0.14f;

    public static int currentlySelectedRow = -1;

    public static void main(String[] args){
        Constants.CreateConstants();
        Logger.Log("Starting up...");

        new File(FILE_LOCATION + "/").mkdir();
        GlobalConfig.CreateStream();
        new Config().Load();
        new Config().Save();

        try {
            if(GlobalConfig.darkMode) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            }else{
                UIManager.setLookAndFeel(new FlatLightLaf());
            }
            Logger.Log("Changed look and feel");
        } catch (UnsupportedLookAndFeelException e) {
            Logger.Error(e.getLocalizedMessage());
        }

        JFrame startupForm = new JFrame("Loading...");
        startupForm.setContentPane(new StartupForm().getStartupPanel());
        startupForm.setUndecorated(true);
        startupForm.setType(Window.Type.UTILITY);
        startupForm.pack();
        startupForm.setLocationRelativeTo(null);
        startupForm.setVisible(true);

        try {
            Thread.sleep(2000); //Enjoy that logo and make the user think we're doing something important
        } catch (InterruptedException e) {
            Logger.Error(e.getLocalizedMessage());
        }

        if(args.length > 0){
            try {
                Files.copy(Paths.get(args[0]), Paths.get(FILE_LOCATION + GlobalConfig.modsLocation + ModLoader.fileComponent(args[0])), StandardCopyOption.REPLACE_EXISTING);
                Logger.Log("Copied files. From:" + args[0] + " To: " + FILE_LOCATION + GlobalConfig.modsLocation + ModLoader.fileComponent(args[0]));
            } catch (IOException e) {
                Logger.Error(e.getLocalizedMessage());
            }
        }

        Logger.Log("Creating frame...");
        projectSelect = new ProjectSelect();
        projectSelectFrame = new JFrame("Project Select");

        ImageIcon img = new ImageIcon(ResourceLoader.class.getResource("dstguimodcreatorlogo.png"));
        projectSelectFrame.setIconImage(img.getImage());
        projectSelectFrame.setContentPane(projectSelect.getProjectSelectPanel());
        projectSelectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Logger.Log("Done!");

        Logger.Log("Setting up mods table and reading mods...");
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        projectSelect.getProjectsListTable().setModel(model);
        model.addColumn("Project Name:");
        model.addColumn("Project Author:");
        model.addColumn("Project Path:");
        readMods();
        Logger.Log("Done!");

        projectSelect.getNewMod().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateNewMod();
            }
        });

        projectSelect.getLoadMod().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadCurrentMod();
            }
        });

        projectSelect.getProjectsListTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = projectSelect.getProjectsListTable().rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    currentlySelectedRow = row;
                }
            }
        });

        projectSelectFrame.pack();
        startupForm.setVisible(false);
        projectSelectFrame.setLocationRelativeTo(null);
        projectSelectFrame.setVisible(true);

        new File(FILE_LOCATION + "/mods").mkdir();
        new File(FILE_LOCATION + "/speech").mkdir();

        Logger.Log("Checking for update...");
        if(Updater.CheckForUpdate(version)){
            Logger.Log("Found update.");
            Updater.GetLastestRelease(projectSelectFrame);
        }
    }

    public static void CreateNewMod(){
        JFrame newModConfigFrame = new JFrame("Mod Creation");
        NewModConfig newModConfig = new NewModConfig();
        newModConfigFrame.setContentPane(newModConfig.getNewModConfigPanel());
        newModConfigFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon img = new ImageIcon(ResourceLoader.class.getResource("dstguimodcreatorlogo.png"));
        newModConfigFrame.setIconImage(img.getImage());

        newModConfig.getCreateMod().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = newModConfig.getModNameTextField().getText();
                String author = newModConfig.getModAuthorTextField().getText();
                projectSelectFrame.setVisible(false);
                newModConfigFrame.setVisible(false);
                ModLoader.CreateMod(FILE_LOCATION + GlobalConfig.modsLocation + name.replace(" ", "") + ".demv", author, name);
            }
        });

        newModConfig.getCancel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newModConfigFrame.dispose();
            }
        });

        newModConfigFrame.pack();
        newModConfigFrame.setLocationRelativeTo(null);
        newModConfigFrame.setVisible(true);
    }

    public static void LoadCurrentMod(){
        if(currentlySelectedRow == -1){
            JOptionPane.showMessageDialog(projectSelectFrame,
                    "No project is selected",
                    "Cannot Load Project",
                    JOptionPane.WARNING_MESSAGE);
        }else{
            projectSelectFrame.setVisible(false);
            ModLoader.LoadMod(FILE_LOCATION + GlobalConfig.modsLocation + projectSelect.getProjectsListTable().getModel().getValueAt(currentlySelectedRow, 2));
        }
    }

    public static void readMods(){
        try{
            DefaultTableModel model = (DefaultTableModel) projectSelect.getProjectsListTable().getModel();
            String[] mods = getAllDirectories(FILE_LOCATION + GlobalConfig.modsLocation);
            for(int i = 0; i < mods.length; i++){
                SaveObject saveObject = SaveSystem.TempLoad(FILE_LOCATION + GlobalConfig.modsLocation + mods[i]);
                model.addRow(new Object[]{saveObject.modName, saveObject.modAuthor, mods[i]});
            }
        }catch (Exception e){
            Logger.Error(e.getMessage());
        }
    }

    public static String[] getAllDirectories(String path){
        File dir = new File(path);
        File [] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".demv");
            }
        });
        List<String> _directories = new ArrayList<String>();

        for(int i = 0; i < files.length; i++){
            _directories.add(files[i].getName());
        }

        return _directories.toArray(new String[0]);
    }
}
