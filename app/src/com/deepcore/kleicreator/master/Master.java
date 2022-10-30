package com.deepcore.kleicreator.master;

import com.deepcore.kleicreator.frames.ProjectSelectDialog;
import com.deepcore.kleicreator.plugin.PluginHandler;
import com.deepcore.kleicreator.sdk.gui.JHelper;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.deepcore.kleicreator.config.Config;
import com.deepcore.kleicreator.config.GlobalConfig;
import com.deepcore.kleicreator.constants.Constants;
import com.deepcore.kleicreator.frames.CreateModDialog;
import com.deepcore.kleicreator.frames.Startup;
import com.deepcore.kleicreator.sdk.logging.Logger;
import com.deepcore.kleicreator.modloader.Mod;
import com.deepcore.kleicreator.modloader.ModLoader;
import org.apache.commons.lang3.exception.ExceptionUtils;
import com.deepcore.kleicreator.resources.ResourceLoader;
import com.deepcore.kleicreator.savesystem.SaveObject;
import com.deepcore.kleicreator.savesystem.SaveSystem;
import com.deepcore.kleicreator.updater.Updater;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.deepcore.kleicreator.constants.Constants.FILE_LOCATION;

public class Master {
    public static ProjectSelectDialog projectSelectDialog;
    public static JFrame projectSelectFrame;
    public static JFrame startupForm;

    public static String version = "v0.0.7";

    public static int currentlySelectedRow = -1;

    public static boolean exit = false;

    public static void Main(String[] args) {
        Constants.CreateConstants();
        Logger.Start();
        Logger.Log("KleiCreator %s. Credits to DeepCore", version);

        PluginHandler.LoadPlugins();
        PluginHandler.TriggerEvent("OnLoad");

        //Create working directories
        new File(FILE_LOCATION + "/").mkdir();
        new File(FILE_LOCATION + "/mods").mkdir();
        new File(FILE_LOCATION + "/speech").mkdir();
        new File(FILE_LOCATION + "/plugins").mkdir();
        GlobalConfig.CreateStream();
        new Config().Load(); //Load if config exists
        new Config().Save(); //Otherwise, save default settings

        try {
            switch (GlobalConfig.theme) {
                case Light:
                    UIManager.setLookAndFeel(new FlatLightLaf());
                    break;
                case Dark:
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                    break;
                case Default:
                    UIManager.setLookAndFeel(new NimbusLookAndFeel());
                    break;
            }
            try (InputStream in = ResourceLoader.class.getResourceAsStream("font.ttf")) {
                setUIFont(Font.createFont(Font.PLAIN, in).deriveFont(15f));
                Logger.Debug("Successfully loaded custom font");
            }

            Logger.Debug("Successfully changed look and feel.");
        } catch (UnsupportedLookAndFeelException | IOException | FontFormatException e) {
            Logger.Error(ExceptionUtils.getStackTrace(e));
        }

        //Create loading form
        startupForm = new JFrame();
        startupForm.setContentPane(new Startup().getStartupPanel());
        startupForm.setUndecorated(true);
        startupForm.setType(Window.Type.UTILITY);
        startupForm.pack();
        startupForm.setLocationRelativeTo(null);
        startupForm.setVisible(true);

        //If arguments, we assume it's a project file and copy it to project directory
        if (args.length > 0) {
            try {
                Files.copy(Paths.get(args[0]), Paths.get(FILE_LOCATION + "/mods/" + ModLoader.fileComponent(args[0])), StandardCopyOption.REPLACE_EXISTING);
                Logger.Debug("Copied project from %s to %s", args[0], FILE_LOCATION + "/mods/" + ModLoader.fileComponent(args[0]));
            } catch (IOException e) {
                Logger.Error(ExceptionUtils.getStackTrace(e));
            }
        }

        Logger.Debug("Instantiating ProjectSelect and setting up frame...");
        projectSelectDialog = new ProjectSelectDialog();
        projectSelectFrame = new JFrame("Select Project");

        ImageIcon img = new ImageIcon(ResourceLoader.class.getResource("dstguimodcreatorlogo.png"));
        projectSelectFrame.setIconImage(img.getImage());
        projectSelectFrame.setContentPane(projectSelectDialog.getProjectSelectPanel());
        projectSelectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultTableModel model = new DefaultTableModel() {
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

        if (GlobalConfig.theme == GlobalConfig.Theme.Dark) {
        } else {
            projectSelectDialog.getConfigButton().setForeground(Color.BLACK);
        }

        projectSelectDialog.getConfigButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame configDialogFrame = new JFrame();
                configDialogFrame.setIconImage(img.getImage());
                JPanel panel = new JPanel();
                configDialogFrame.setContentPane(panel);

                panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

                panel.add(JHelper.CreateTitleJLabel("KleiCreator"));

                JComboBox theme = new JComboBox();
                panel.add(theme);
                for (GlobalConfig.Theme t : GlobalConfig.Theme.values()) {
                    theme.addItem(t.toString());
                }
                theme.setSelectedIndex(GlobalConfig.theme.ordinal());

                JCheckBox askSaveOnLeave = new JCheckBox("Ask Save On Leave");
                panel.add(askSaveOnLeave);
                askSaveOnLeave.setSelected(GlobalConfig.askSaveOnLeave);

                PluginHandler.TriggerEvent("OnConfigSetup", configDialogFrame);

                JButton saveButton = new JButton("Save and Restart");
                saveButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
                panel.add(saveButton);
                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GlobalConfig.theme = GlobalConfig.Theme.valueOf(theme.getSelectedItem().toString());
                        GlobalConfig.askSaveOnLeave = askSaveOnLeave.isSelected();
                        PluginHandler.TriggerEvent("OnConfigSave", configDialogFrame);
                        new Config().Save();
                        Starter.startCounter++;
                        exit = true;
                        configDialogFrame.dispose();
                    }
                });

                panel.setLayout(new GridLayout(panel.getComponentCount(),1, 7, 7));

                configDialogFrame.pack();
                configDialogFrame.setLocationRelativeTo(null);
                configDialogFrame.setVisible(true);
            }
        });

        Logger.Debug("Successfully setup project select.");

        Logger.Debug("Checking for update...");
        if (Updater.CheckForUpdate(version)) {
            Logger.Log("Found update.");
            Updater.GetLastestRelease(projectSelectFrame);
        }

        // This is here so we look important
        long time = Calendar.getInstance().getTime().getTime() - Logger.startTime.getTime();
        if(time < 2000){
            try {
                Thread.sleep(2000-time);
            } catch (InterruptedException e) {

            }
        }

        PluginHandler.TriggerEvent("OnStartup");

        projectSelectFrame.pack();
        startupForm.setVisible(false);
        projectSelectFrame.setLocationRelativeTo(null);
        projectSelectFrame.setVisible(true);

        while (!exit) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {

            }
        }
        exit = false;
        projectSelectFrame.dispose();
        Logger.Debug("Stopping...");
        return;
    }

    public static void CreateNewMod() {
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
                Mod.modName = name;
                ModLoader.CreateMod(FILE_LOCATION + "/mods/" + Mod.escapedModName() + ".proj", author, name);
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

    public static void LoadCurrentMod() {
        startupForm.setVisible(true);
        if (currentlySelectedRow == -1) {
            JOptionPane.showMessageDialog(projectSelectFrame,
                    "No project is selected",
                    "Cannot Load Project",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            projectSelectFrame.setVisible(false);
            ModLoader.LoadMod(FILE_LOCATION + "/mods/" + projectSelectDialog.getProjectsListTable().getModel().getValueAt(currentlySelectedRow, 2));
        }
        startupForm.setVisible(false);
    }

    public static void readMods() {
        try {
            DefaultTableModel model = (DefaultTableModel) projectSelectDialog.getProjectsListTable().getModel();
            String[] mods = getAllDirectories(FILE_LOCATION + "/mods/");
            for (int i = 0; i < mods.length; i++) {
                SaveObject saveObject = SaveSystem.TempLoad(FILE_LOCATION + "/mods/" + mods[i]);
                if(saveObject == null){
                    model.addRow(new Object[]{mods[i], "Modded - Cannot load", "Modded - Cannot load"});
                }else{
                    model.addRow(new Object[]{saveObject.modName, saveObject.modAuthor, mods[i]});
                }

            }
        } catch (Exception e) {
            Logger.Error(ExceptionUtils.getStackTrace(e));
        }
    }

    public static String[] getAllDirectories(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".proj");
            }
        });
        List<String> _directories = new ArrayList<String>();

        for (int i = 0; i < files.length; i++) {
            _directories.add(files[i].getName());
        }

        return _directories.toArray(new String[0]);
    }

    public static void setUIFont(Font f) {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, f);
        }
    }
}
