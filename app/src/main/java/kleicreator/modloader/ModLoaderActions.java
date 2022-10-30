package kleicreator.modloader;

import kleicreator.export.Exporter;
import kleicreator.frames.SpeechDialog;
import kleicreator.items.Item;
import kleicreator.modloader.resources.ResourceManager;
import kleicreator.recipes.RecipeLoader;
import kleicreator.sdk.constants.Constants;
import kleicreator.sdk.logging.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class ModLoaderActions extends ModLoader {
    public static void SetupListeners() {
        modEditor.getModItemCreate().addActionListener(e -> {
            Mod.items.add(new Item());
            Update();
            modEditor.getModItemSelect().setSelectedIndex(Mod.items.size() - 1);
            Logger.Debug("Created Item");
        });

        modEditor.getModItemDelete().addActionListener(e -> {
            Mod.items.remove(Mod.items.get(modEditor.getModItemSelect().getSelectedIndex()));
            Update();
            Logger.Debug("Deleted Item");
        });

        modEditor.getSaveAll().addActionListener(e -> {
            SaveAll();
            Logger.Debug("Saved All");
        });


        modEditor.getResourcesAdd().addActionListener(e -> {
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
                    if(texFile == null){
                        return;
                    }

                    chooser.removeChoosableFileFilter(tex);
                    chooser.addChoosableFileFilter(xml);
                    JOptionPane.showMessageDialog(modEditorFrame, chooser, "Open XML file", JOptionPane.QUESTION_MESSAGE);
                    File xmlFile = chooser.getSelectedFile();
                    if(xmlFile == null){
                        return;
                    }

                    ResourceManager.CreateTexture(texFile.getAbsolutePath(), xmlFile.getAbsolutePath(), ResourceManager.TextureType.values()[(getOption("Texture location", new Object[]{"Inventory Image", "Mod Icon", "Portrait", "Map Icon"}))]);
                    Logger.Debug("Created new texture");
                    break;
                case 1:
                    final SpeechDialog speech = new SpeechDialog();
                    Logger.Debug("Created speech creation dialog");
                    JOptionPane.showMessageDialog(modEditorFrame, speech.getSpeechConfigPanel());

                    ResourceManager.CreateSpeech(speech.getSpeechNameTextField().getText());
                    Update();
                    Logger.Debug("Created new speech resource");
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
        });

        modEditor.getResourcesRemove().addActionListener(e -> {
            ResourceManager.resources.remove(modEditor.getResourcesTable().getSelectedRow());
            Update();
            Logger.Debug("Removed resource");
        });

        modEditor.getModItemSave().addActionListener(e -> {
            SaveItem();
            Update();
            Logger.Debug("Saved Item");
        });

        modEditor.getModConfigSave().addActionListener(e -> {
            SaveModConfig();
            Update();
            Logger.Debug("Saved mod config");
        });

        modEditor.getModItemSelect().addActionListener(e -> Update());

        modEditor.getModExport().addActionListener(e -> {
            Exporter.Export();
            Logger.Debug("Exported");
        });

        modEditor.getModSpeechReloadSpeech().addActionListener(e -> {
            ReloadSpeech();
            Update();
            Logger.Debug("Reloaded speech resources");
        });

        modEditor.getOpenFolder().addActionListener(e -> {
            try {
                Desktop.getDesktop().open(new File(Constants.constants.KLEICREATOR_LOCATION + "/speech/"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        modEditor.getModRecipesCreate().addActionListener(e -> RecipeLoader.CreateNewRecipe());

        modEditor.getModRecipesDelete().addActionListener(e -> RecipeLoader.DeleteRecipe());

        modEditor.getModRecipesSelector().addActionListener(e -> Update());
    }
}
