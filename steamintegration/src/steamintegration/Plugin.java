package steamintegration;

import com.alee.utils.ExceptionUtils;
import com.deepcore.kleicreator.sdk.Application;
import com.deepcore.kleicreator.sdk.config.Config;
import com.deepcore.kleicreator.sdk.gui.JHelper;
import com.deepcore.kleicreator.sdk.gui.ModEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;

public class Plugin implements com.deepcore.kleicreator.sdk.Plugin {
    public static String steamPath;

    public JTextField steamPathTextField;
    public JPanel tab;

    @Override
    public String name() {
        return "Steam Integration";
    }

    @Override
    public String id() {
        return "steamintegration";
    }

    @Override
    public String author() {
        return "DeepCore";
    }

    @Override
    public void onload() {
        com.deepcore.kleicreator.sdk.Plugin.super.onload();
        logger.Log("Loaded Steam Integration");
    }

    @Override
    public void onstartup() {
        Config.AssertValue("steamPath", GameHandler.GetDefaultSteamPath().toAbsolutePath().toString());
        steamPath = (String) Config.LoadValue("steamPath");

        logger.Log("Loaded steam path as %s", steamPath);

        GameHandler.CheckSteamIntegration();

        if(GameHandler.GameIntegration){
            logger.Log("Copying client-side mod");
            logger.Log(GameHandler.ModsFolder + "/kleicreatorsteamintegration");
            try {
                copyFromJar("/kleicreatorsteamintegration/", Paths.get(GameHandler.ModsFolder + "/kleicreatorsteamintegration/"));
            } catch (Exception e) {
                logger.Error(ExceptionUtils.getStackTrace(e));
            }
        }
    }

    @Override
    public void onmodload() {
        tab = new JPanel();
        ModEditor.AddTab("Steam Integration", tab);
        tab.add(JHelper.CreateTitleJLabel("Steam Integration"));
        if(GameHandler.GameIntegration){
            tab.add(new JLabel("Steam Integration enabled"));
            JButton reloadItemLists = new JButton("Reload item lists");
            JButton exportAndRun = new JButton("Export and run");



        }else{
            tab.add(new JLabel("Integration not enabled. Change steam path in config and retry."));
        }
        tab.setLayout(new BoxLayout(tab, BoxLayout.Y_AXIS));
    }

    @Override
    public void onmodeditorupdate() {

    }

    @Override
    public void onconfigdialogsetup(JFrame configFrame) {
        configFrame.getContentPane().add(JHelper.CreateTitleJLabel("Steam Integration"));
        steamPathTextField = new JTextField();
        steamPathTextField.setText(steamPath);
        configFrame.getContentPane().add(steamPathTextField);
    }

    @Override
    public void onconfigdialogsave(JFrame configFrame) {
        Config.SaveValue("steamPath", steamPathTextField.getText());
    }

    public void copyFromJar(String source, final Path target) throws URISyntaxException, IOException {
        URI resource = getClass().getResource("").toURI();
        FileSystem fileSystem = FileSystems.newFileSystem(
                resource,
                Collections.<String, String>emptyMap()
        );


        final Path jarPath = fileSystem.getPath(source);

        Files.walkFileTree(jarPath, new SimpleFileVisitor<Path>() {

            private Path currentTarget;

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                currentTarget = target.resolve(jarPath.relativize(dir).toString());
                Files.createDirectories(currentTarget);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.copy(file, target.resolve(jarPath.relativize(file).toString()), StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }

        });
    }
}
