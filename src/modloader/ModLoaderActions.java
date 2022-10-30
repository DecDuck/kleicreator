package modloader;

import export.Exporter;
import frames.SpeechDialog;
import logging.Logger;
import items.Item;
import modloader.resources.ResourceManager;
import recipes.RecipeLoader;
import resources.ResourceLoader;
import speech.SpeechFile;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static constants.Constants.FILE_LOCATION;

public class ModLoaderActions extends ModLoader{
    public static void SetupListeners(){
        modEditor.getModItemCreate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mod.items.add(new Item());
                Update();
                ModLoader.modEditor.getModItemSelect().setSelectedIndex(Mod.items.size()-1);
                Logger.Log("Created Item");
            }
        });

        modEditor.getModItemDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mod.items.remove(Mod.items.get(modEditor.getModItemSelect().getSelectedIndex()));
                Update();
                Logger.Log("Deleted Item");
            }
        });

        modEditor.getSaveAll().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveAll();
                Logger.Log("Saved All");
            }
        });

        modEditor.getResourcesAdd().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch(ModLoader.getOption("Type of resource", new Object[]{ "Texture", "Speech", })){
                    case 0:
                        Logger.Log("Importing texture....");
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

                        ResourceManager.CreateResource(texFile.getAbsolutePath(), xmlFile.getAbsolutePath(), ResourceManager.TextureLocation.values()[(ModLoader.getOption("Texture location", new Object[] {"Inventory Image", "Mod Icon", "Portrait", "Map Icon"}))]);
                        Logger.Log("Created new texture");
                        break;
                    case 1:
                        JFrame speechConfigFrame = new JFrame("Create New Speech File");
                        ImageIcon img = new ImageIcon(ResourceLoader.class.getResource("dstguimodcreatorlogo.png"));
                        speechConfigFrame.setIconImage(img.getImage());
                        SpeechDialog speech = new SpeechDialog();
                        speechConfigFrame.setContentPane(speech.getSpeechConfigPanel());
                        speechConfigFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

                        speech.getSpeechCreate().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ResourceManager.CreateResource(SpeechFile.SpeechType.Character, speech.getSpeechNameTextField().getText());
                                speechConfigFrame.dispose();
                                Update();
                                Logger.Log("Created new speech resource");
                            }
                        });

                        speechConfigFrame.pack();
                        speechConfigFrame.setLocationRelativeTo(null);
                        speechConfigFrame.setVisible(true);
                        Logger.Log("Created speech creation dialog");
                        break;
                    case 2:
                        JFileChooser animChooser = new JFileChooser();
                        animChooser.setAcceptAllFileFilterUsed(false);
                        FileNameExtensionFilter anim = new FileNameExtensionFilter("Animation File", "zip");

                        animChooser.addChoosableFileFilter(anim);
                        JOptionPane.showMessageDialog(modEditorFrame, animChooser, "Open Animation file", JOptionPane.QUESTION_MESSAGE);
                        File animationFile = animChooser.getSelectedFile();
                        ResourceManager.CreateResource(animationFile.getAbsolutePath());
                        Logger.Log("Imported animation");
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
                Logger.Log("Removed resource");
            }
        });

        modEditor.getModItemSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveItem();
                Update();
                Logger.Log("Saved Item");
            }
        });

        modEditor.getModConfigSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveModConfig();
                Update();
                Logger.Log("Saved mod config");
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
                Logger.Log("Exported");
            }
        });

        modEditor.getModSpeechReloadSpeech().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReloadSpeech();
                Update();
                Logger.Log("Reloaded speech resources");
            }
        });

        modEditor.getOpenFolder().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File(FILE_LOCATION + "/speech/"));
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
