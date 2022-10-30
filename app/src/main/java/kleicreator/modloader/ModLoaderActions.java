package kleicreator.modloader;

import kleicreator.export.Exporter;
import kleicreator.frames.SpeechDialog;
import kleicreator.items.Item;
import kleicreator.modloader.resources.ResourceManager;
import kleicreator.recipes.RecipeLoader;
import kleicreator.sdk.logging.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static kleicreator.sdk.constants.Constants.FILE_LOCATION;

public class ModLoaderActions extends ModLoader {
    public static void SetupListeners() {
        modEditor.getModItemCreate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mod.items.add(new Item());
                Update();
                modEditor.getModItemSelect().setSelectedIndex(Mod.items.size() - 1);
                Logger.Debug("Created Item");
            }
        });

        modEditor.getModItemDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mod.items.remove(Mod.items.get(modEditor.getModItemSelect().getSelectedIndex()));
                Update();
                Logger.Debug("Deleted Item");
            }
        });

        modEditor.getSaveAll().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveAll();
                Logger.Debug("Saved All");
            }
        });

        modEditor.getResourcesAdd().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (getOption("Type of resource", new Object[]{"Texture", "Speech",})) {
                    case 0:
                        Logger.Debug("Importing texture....");
                        JFileChooser chooser = new JFileChooser();
                        chooser.setAcceptAllFileFilterUsed(false);
                        FileNameExtensionFilter tex = new FileNameExtensionFilter("TEX File", "tex");
                        FileNameExtensionFilter xml = new FileNameExtensionFilter("XML File", "xml");

                        chooser.addChoosableFileFilter(tex);
                        JOptionPane.showMessageDialog(modEditorFrame, chooser, "Open TEX file", JOptionPane.QUESTION_MESSAGE);
                        File texFile = chooser.getSelectedFile();

                        chooser.removeChoosableFileFilter(tex);
                        chooser.addChoosableFileFilter(xml);
                        JOptionPane.showMessageDialog(modEditorFrame, chooser, "Open XML file", JOptionPane.QUESTION_MESSAGE);
                        File xmlFile = chooser.getSelectedFile();

                        ResourceManager.CreateTexture(texFile.getAbsolutePath(), xmlFile.getAbsolutePath(), ResourceManager.TextureLocation.values()[(getOption("Texture location", new Object[]{"Inventory Image", "Mod Icon", "Portrait", "Map Icon"}))]);
                        Logger.Debug("Created new texture");
                        break;
                    case 1:
                        final JFrame speechConfigFrame = new JFrame("Create New Speech File");
                        ImageIcon img = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("dstguimodcreatorlogo.png"));
                        speechConfigFrame.setIconImage(img.getImage());
                        final SpeechDialog speech = new SpeechDialog();
                        speechConfigFrame.setContentPane(speech.getSpeechConfigPanel());
                        speechConfigFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

                        speech.getSpeechCreate().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ResourceManager.CreateSpeech(speech.getSpeechNameTextField().getText());
                                speechConfigFrame.dispose();
                                Update();
                                Logger.Debug("Created new com.deepcore.kleicreator.speech resource");
                            }
                        });

                        speechConfigFrame.pack();
                        speechConfigFrame.setLocationRelativeTo(null);
                        speechConfigFrame.setVisible(true);
                        Logger.Debug("Created com.deepcore.kleicreator.speech creation dialog");
                        break;
                    case 2:
                        JFileChooser animChooser = new JFileChooser();
                        animChooser.setAcceptAllFileFilterUsed(false);
                        FileNameExtensionFilter anim = new FileNameExtensionFilter("Animation File", "zip");

                        animChooser.addChoosableFileFilter(anim);
                        JOptionPane.showMessageDialog(modEditorFrame, animChooser, "Open Animation file", JOptionPane.QUESTION_MESSAGE);
                        File animationFile = animChooser.getSelectedFile();
                        ResourceManager.CreateAnimation(animationFile.getAbsolutePath());
                        Logger.Debug("Imported animation");
                        break;
                }

                Update();
            }
        });

        modEditor.getResourcesRemove().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResourceManager.resources.remove(modEditor.getResourcesTable().getSelectedRow());
                Update();
                Logger.Debug("Removed resource");
            }
        });

        modEditor.getModItemSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveItem();
                Update();
                Logger.Debug("Saved Item");
            }
        });

        modEditor.getModConfigSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveModConfig();
                Update();
                Logger.Debug("Saved mod config");
            }
        });

        modEditor.getModItemSelect().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Update();
            }
        });

        modEditor.getModExport().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Exporter.Export();
                Logger.Debug("Exported");
            }
        });

        modEditor.getModSpeechReloadSpeech().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReloadSpeech();
                Update();
                Logger.Debug("Reloaded speech resources");
            }
        });

        modEditor.getOpenFolder().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File(FILE_LOCATION + "/kleicreator/speech/"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        modEditor.getModRecipesCreate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RecipeLoader.CreateNewRecipe();
            }
        });

        modEditor.getModRecipesDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RecipeLoader.DeleteRecipe();
            }
        });

        modEditor.getModRecipesSelector().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Update();
            }
        });
    }
}
