package master;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import config.Config;
import config.GlobalConfig;
import constants.Constants;
import logging.Logger;
import frames.*;
import modloader.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import resources.ResourceLoader;
import savesystem.*;
import updater.Updater;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static constants.Constants.FILE_LOCATION;

public class Master {
    public static ProjectSelectDialog projectSelectDialog;
    public static JFrame projectSelectFrame;

    public static String version = "v0.0.6";

    public static int currentlySelectedRow = -1;

    public static void main(String[] args){
        Logger.Start();
        Logger.Log("KleiCreator %s. Credits to decduck3", version);
        Constants.CreateConstants();

        //Create working directories
        new File(FILE_LOCATION + "/").mkdir();
        new File(FILE_LOCATION + "/mods").mkdir();
        new File(FILE_LOCATION + "/speech").mkdir();
        GlobalConfig.CreateStream();
        Config c = new Config();
        c.Load(); //Load if config exists
        c.Save(); //Otherwise, save default settings

        try {
            if(GlobalConfig.darkMode) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            }else{
                UIManager.setLookAndFeel(new FlatLightLaf());
            }
            try (InputStream in = ResourceLoader.class.getResourceAsStream("font.ttf")){
                setUIFont (Font.createFont(Font.PLAIN, in).deriveFont(15f));
                Logger.Log("Successfully loaded custom font");
            }
            Logger.Log("Successfully changed look and feel.");
        } catch (UnsupportedLookAndFeelException | IOException | FontFormatException e) {
            Logger.Error(ExceptionUtils.getStackTrace(e));
        }

        //Create loading form
        JFrame startupForm = new JFrame();
        startupForm.setContentPane(new Startup().getStartupPanel());
        startupForm.setUndecorated(true);
        startupForm.setType(Window.Type.UTILITY);
        startupForm.pack();
        startupForm.setLocationRelativeTo(null);
        startupForm.setVisible(true);

        try {
            Thread.sleep(1500); //Enjoy that logo and make the user think we're doing something important
        } catch (InterruptedException e) {
            Logger.Error(ExceptionUtils.getStackTrace(e));
        }

        //If arguments, we assume it's a project file and copy it to project directory
        if(args.length > 0){
            try {
                Files.copy(Paths.get(args[0]), Paths.get(FILE_LOCATION + GlobalConfig.modsLocation + ModLoader.fileComponent(args[0])), StandardCopyOption.REPLACE_EXISTING);
                Logger.Log("Copied project from %s to %s", args[0], FILE_LOCATION + GlobalConfig.modsLocation + ModLoader.fileComponent(args[0]));
            } catch (IOException e) {
                Logger.Error(ExceptionUtils.getStackTrace(e));
            }
        }

        Logger.Log("Instantiating ProjectSelect and setting up frame...");
        projectSelectDialog = new ProjectSelectDialog();
        projectSelectFrame = new JFrame("Select Project");

        ImageIcon img = new ImageIcon(ResourceLoader.class.getResource("dstguimodcreatorlogo.png"));
        projectSelectFrame.setIconImage(img.getImage());
        projectSelectFrame.setContentPane(projectSelectDialog.getProjectSelectPanel());
        projectSelectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        projectSelectDialog.getProjectsListTable().setModel(model);
        model.addColumn("Project Name:");
        model.addColumn("Project Author:");
        model.addColumn("Project Path:");
        readMods();

        projectSelectDialog.getNewMod().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateNewMod();
            }
        });

        projectSelectDialog.getLoadMod().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadCurrentMod();
            }
        });

        projectSelectDialog.getProjectsListTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = projectSelectDialog.getProjectsListTable().rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    currentlySelectedRow = row;
                }
            }
        });

        if(GlobalConfig.darkMode){
        }else{
            projectSelectDialog.getConfigButton().setForeground(Color.BLACK);
        }

        projectSelectDialog.getConfigButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame configEditorFrame = new JFrame();
                ConfigDialog configDialog = new ConfigDialog();
                configEditorFrame.setContentPane(configDialog.getFrame());
                configDialog.getDarkModeCheckBox().setSelected(GlobalConfig.darkMode);
                configDialog.getAskSaveOnLeaveCheckBox().setSelected(GlobalConfig.askSaveOnLeave);
                configDialog.getSave().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GlobalConfig.darkMode = configDialog.getDarkModeCheckBox().isSelected();
                        GlobalConfig.askSaveOnLeave = configDialog.getAskSaveOnLeaveCheckBox().isSelected();
                        new Config().Save();
                        System.exit(0);
                    }
                });

                configEditorFrame.pack();
                configEditorFrame.setLocationRelativeTo(null);
                configEditorFrame.setVisible(true);
            }
        });

        Logger.Log("Successfully setup project select.");

        projectSelectFrame.pack();
        startupForm.setVisible(false);
        projectSelectFrame.setLocationRelativeTo(null);
        projectSelectFrame.setVisible(true);

        Logger.Log("Checking for update...");
        if(Updater.CheckForUpdate(version)){
            Logger.Log("Found update.");
            Updater.GetLastestRelease(projectSelectFrame);
        }
    }

    public static void CreateNewMod(){
        JFrame newModConfigFrame = new JFrame("Create New Project");
        CreateModDialog createModDialog = new CreateModDialog();
        newModConfigFrame.setContentPane(createModDialog.getNewModConfigPanel());
        newModConfigFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon img = new ImageIcon(ResourceLoader.class.getResource("dstguimodcreatorlogo.png"));
        newModConfigFrame.setIconImage(img.getImage());

        createModDialog.getCreateMod().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = createModDialog.getModNameTextField().getText();
                String author = createModDialog.getModAuthorTextField().getText();
                projectSelectFrame.setVisible(false);
                newModConfigFrame.setVisible(false);
                Mod _temp = new Mod();
                _temp.modName = name;
                ModLoader.CreateMod(FILE_LOCATION + GlobalConfig.modsLocation + _temp.escapedModName() + ".proj", author, name);
            }
        });

        createModDialog.getCancel().addActionListener(new ActionListener() {
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
            ModLoader.LoadMod(FILE_LOCATION + GlobalConfig.modsLocation + projectSelectDialog.getProjectsListTable().getModel().getValueAt(currentlySelectedRow, 2));
        }
    }

    public static void readMods(){
        try{
            DefaultTableModel model = (DefaultTableModel) projectSelectDialog.getProjectsListTable().getModel();
            String[] mods = getAllDirectories(FILE_LOCATION + GlobalConfig.modsLocation);
            for(int i = 0; i < mods.length; i++){
                SaveObject saveObject = SaveSystem.TempLoad(FILE_LOCATION + GlobalConfig.modsLocation + mods[i]);
                model.addRow(new Object[]{saveObject.modName, saveObject.modAuthor, mods[i]});
            }
        }catch (Exception e){
            Logger.Error(ExceptionUtils.getStackTrace(e));
        }
    }

    public static String[] getAllDirectories(String path){
        File dir = new File(path);
        File [] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".proj");
            }
        });
        List<String> _directories = new ArrayList<String>();

        for(int i = 0; i < files.length; i++){
            _directories.add(files[i].getName());
        }

        return _directories.toArray(new String[0]);
    }

    public static void setUIFont (Font f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }
}