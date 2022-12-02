package kleicreator;

import com.formdev.flatlaf.FlatDarkLaf;
import kleicreator.export.Exporter;
import kleicreator.frames.*;
import kleicreator.data.Config;
import kleicreator.data.Constants;
import kleicreator.util.ArgumentParser;
import kleicreator.sdk.internal.PluginHandler;
import kleicreator.util.Logger;
import kleicreator.util.Updater;
import kleicreator.data.Mod;
import kleicreator.savesystem.SaveObject;
import kleicreator.savesystem.SaveSystem;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.*;
import java.util.List;

import static kleicreator.Master.GlobalTheme.*;

public class Master {
    public static ProjectSelectDialog projectSelectDialog;
    public static JFrame projectSelectFrame;
    //public static JFrame startupForm;
    public static String version;
    public static int currentlySelectedRow = -1;
    public static boolean exit = false;
    public static ImageIcon icon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("kleicreator/kleicreator_square.png"));
    public static boolean darkMode = true;

    public enum GlobalTheme {
        Light,
        Dark,
        Default
    }

    public static void Main(String[] args) {
        String p = Master.class.getPackage().getImplementationVersion();
        if (p != null) {
            version = "v" + p;
        } else {
            version = "[DEV]";
        }
        /*
        try {

            version = "v" + new Scanner(ClassLoader.getSystemClassLoader().getResource("version").openStream(), "UTF-8").next();
        } catch (IOException e) {
            version = "v0.0.0"; // Make sure we download a new version cause this one's broken as hell
        }
        */

        ArgumentParser.ParseArguments(args);
        Constants.constants = new Constants();
        Constants.constants.CreateConstants();
        Logger.Start();
        Logger.Log("KleiCreator %s. Developed by DecDuck", version);
        Logger.Log("Started with arguments: " + String.join(" ", args));

        //Create working directories
        new File(Constants.constants.KLEICREATOR_LOCATION + "/").mkdir();
        new File(Constants.constants.KLEICREATOR_LOCATION + "/mods").mkdir();
        new File(Constants.constants.KLEICREATOR_LOCATION + "/speech").mkdir();
        new File(Constants.constants.KLEICREATOR_LOCATION + "/plugins").mkdir();
        new File(Constants.constants.KLEICREATOR_LOCATION + "/config").mkdir();
        new File(Constants.constants.KLEICREATOR_LOCATION + "/data").mkdir();
        Config.AssertDataset("kleicreator");
        Config.SaveData("kleicreator.theme", Dark, false);
        Config.SaveData("kleicreator.asksaveonleave", true, false);
        Config.SaveData("kleicreator.copyresources", false, false);

        FlatDarkLaf.setGlobalExtraDefaults( Collections.singletonMap( "@accentColor", "#ffb400" ) );
        FlatDarkLaf.setup();

        // Custom theming
        UIManager.put("Button.arc", 0);
        UIManager.put("Component.arc", 0);
        UIManager.put("CheckBox.arc", 0);
        UIManager.put("ProgressBar.arc", 0);

        UIManager.put("Component.arrowType", "triangle");
        //UIManager.put( "TabbedPane.showTabSeparators", true );

        UIManager.put("ScrollBar.showButtons", false);
        UIManager.put("ScrollBar.thumbArc", 999);
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));

        Logger.Debug("Successfully changed look and feel.");

        PluginHandler.LoadPlugins();
        Logger.Debug("Loaded plugins");
        PluginHandler.TriggerEvent("OnLoad");

        if (ArgumentParser.doubleArguments.containsKey("--project")) {
            ModLoader.LoadMod(ArgumentParser.doubleArguments.get("--project"));
            if (ArgumentParser.singleArguments.contains("--export")) {
                new Exporter();
            }
        } else {
            LoadingStartup startup = new LoadingStartup();
            startup.SetProgress(0, "Starting up...");

            startup.SetProgress(10, "Setting up project select dialog...");
            Logger.Debug("Instantiating ProjectSelect and setting up frame...");
            projectSelectDialog = new ProjectSelectDialog();
            projectSelectFrame = new JFrame("KleiCreator " + version + " | Project Select");

            projectSelectFrame.setIconImage(icon.getImage());
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

            projectSelectDialog.getConfigButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame configDialogFrame = new JFrame();
                    configDialogFrame.setIconImage(Master.icon.getImage());
                    JPanel panel = new JPanel();
                    configDialogFrame.setContentPane(panel);

                    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

                    panel.add(new JLabel("KleiCreator"));

                    //JComboBox theme = new JComboBox();
                    //panel.add(theme);
                    //for (GlobalTheme t : GlobalTheme.values()) {
                    //    theme.addItem(t.toString());
                    //}
                    //theme.setSelectedIndex(((GlobalTheme) Config.GetData("kleicreator.theme")).ordinal());

                    JCheckBox askSaveOnLeave = new JCheckBox("Ask Save On Leave");
                    panel.add(askSaveOnLeave);
                    askSaveOnLeave.setSelected((Boolean) Config.GetData("kleicreator.asksaveonleave"));

                    JCheckBox copyResources = new JCheckBox("Copy Resources When Importing");
                    panel.add(copyResources);
                    copyResources.setSelected((Boolean) Config.GetData("kleicreator.copyresources"));

                    JButton saveButton = new JButton("Save and Restart");
                    saveButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
                    saveButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Config.SaveData("kleicreator.theme", GlobalTheme.valueOf(theme.getSelectedItem().toString()));
                            Config.SaveData("kleicreator.asksaveonleave", askSaveOnLeave.isSelected());
                            Config.SaveData("kleicreator.copyresources", copyResources.isSelected());
                            PluginHandler.TriggerEvent("OnConfigSave", configDialogFrame);
                            Starter.startCounter++;
                            exit = true;
                            configDialogFrame.dispose();
                        }
                    });

                    PluginHandler.TriggerEvent("OnConfigSetup", configDialogFrame);

                    panel.add(saveButton);

                    panel.setLayout(new GridLayout(panel.getComponentCount(), 1, 7, 7));

                    configDialogFrame.pack();

                    configDialogFrame.setLocationRelativeTo(null);
                    configDialogFrame.setVisible(true);
                }
            });

            startup.SetProgress(90, "Finished setting up project select dialog");
            Logger.Debug("Successfully setup project select.");

            startup.SetProgress(95, "Checking for update...");
            Logger.Debug("Checking for update...");
            if (Updater.CheckForUpdate(version)) {
                Logger.Log("Found update.");
                Updater.GetLatestRelease(projectSelectFrame);
            }

            startup.SetProgress(100, "Starting...");
            // This is here so we look important
            long time = Calendar.getInstance().getTime().getTime() - Logger.startTime.getTime();
            if (time < 2000) {
                try {
                    Thread.sleep(2000 - time);
                } catch (InterruptedException e) {

                }
            }

            PluginHandler.TriggerEvent("OnStartup");

            projectSelectFrame.pack();
            //startupForm.setVisible(false);
            startup.Destroy();
            projectSelectFrame.setLocationRelativeTo(null);
            projectSelectFrame.setVisible(true);

        }


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

    public static void OpenProjectFromFiles(JFrame frame){
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "KleiCreator project files", "proj");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(frame);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            frame.dispose();
            ModLoader.LoadMod(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    public static void CreateNewMod() {
        final JFrame newModConfigFrame = new JFrame("Create New Project");
        final CreateModDialog createModDialog = new CreateModDialog();
        newModConfigFrame.setContentPane(createModDialog.getNewModConfigPanel());
        newModConfigFrame.setDefaultCloseOperation(newModConfigFrame.DISPOSE_ON_CLOSE);
        newModConfigFrame.setIconImage(icon.getImage());

        createModDialog.getCreateMod().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = createModDialog.getModNameTextField().getText();

                if(name.isBlank()) {
                    JOptionPane.showConfirmDialog(null, "Mod name cannot be empty!", "Error", JOptionPane.DEFAULT_OPTION);

                    return;
                }

                String author = createModDialog.getModAuthorTextField().getText();
                projectSelectFrame.setVisible(false);
                newModConfigFrame.setVisible(false);
                Mod _temp = new Mod();
                Mod.modName = name;
                ModLoader.CreateMod(Constants.constants.FetchModLocation() + Mod.escapedModName() + ".proj", author, name);
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
        //startupForm.setVisible(true);
        LoadingStartup startup = new LoadingStartup();
        startup.SetProgress(10, "Loading mod...");
        if (currentlySelectedRow == -1) {
            JOptionPane.showMessageDialog(projectSelectFrame,
                    "No project is selected",
                    "Cannot Load Project",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            projectSelectFrame.setVisible(false);
            ModLoader.LoadMod(Constants.constants.FetchModLocation() + projectSelectDialog.getProjectsListTable().getModel().getValueAt(currentlySelectedRow, 2));
        }
        startup.Destroy();
        //startupForm.setVisible(false);
    }

    public static void readMods() {
        try {
            DefaultTableModel model = (DefaultTableModel) projectSelectDialog.getProjectsListTable().getModel();
            String[] mods = getAllDirectories(Constants.constants.FetchModLocation());
            for (int i = 0; i < mods.length; i++) {
                SaveObject saveObject = SaveSystem.TempLoad(Constants.constants.FetchModLocation() + mods[i]);
                if (saveObject == null) {
                    model.addRow(new Object[]{mods[i], "Modded - Cannot load", "Modded - Cannot load"});
                } else {
                    model.addRow(new Object[]{saveObject.modName, saveObject.modAuthor, mods[i]});
                }

            }
        } catch (Exception e) {
            Logger.Error(e);
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
