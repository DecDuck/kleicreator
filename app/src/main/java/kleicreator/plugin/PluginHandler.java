package kleicreator.plugin;

import kleicreator.modloader.ModLoader;
import kleicreator.sdk.constants.Constants;
import kleicreator.items.Item;
import kleicreator.sdk.EventTrigger;
import kleicreator.sdk.Plugin;
import kleicreator.sdk.item.ItemComponent;
import kleicreator.sdk.logging.Logger;
import org.apache.commons.lang3.exception.ExceptionUtils;
import kleicreator.savesystem.SaveSystem;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PluginHandler {

    public static List<PluginBlob> blobs = new ArrayList<>();

    public static void LoadPlugins(){
        try {
            String[] jarFiles = new File(Constants.FILE_LOCATION + "/plugins/").list();
            if (jarFiles.length > 0){
                for(String jarLocation : jarFiles){
                    File pluginFile = new File(Constants.FILE_LOCATION + "/plugins/" + jarLocation);
                    PluginBlob blob = FetchMetadata(pluginFile);
                    if(blob != null){
                        blobs.add(blob);
                    }

                }
            }

            Collections.sort(blobs);

            for(PluginBlob blob : blobs){
                LoadPlugin(blob);
                Logger.Log(String.valueOf(blob.core.Id()));
            }

        } catch (Exception e) {
            Logger.Error(ExceptionUtils.getStackTrace(e));
        }
    }

    public static PluginBlob FetchMetadata(File pluginFile){
        try {
            PluginBlob blob = new PluginBlob();
            blob.pluginFile = pluginFile;

            JarFile jarFile = new JarFile(pluginFile);
            for(JarEntry entry : Collections.list(jarFile.entries())){
                if(entry.getName().equalsIgnoreCase("plugin.data")){
                    InputStream stream = jarFile.getInputStream(entry);
                    Scanner s = new Scanner(stream).useDelimiter("\\A");
                    String result = s.hasNext() ? s.next() : "";
                    String[] lines = result.split("\n");
                    for(String line : lines){
                        String[] parts = line.split(":");
                        if(parts[0].equalsIgnoreCase("loadorder")){
                            blob.metadata.loadorder = Integer.parseInt(parts[1].strip());
                        }
                    }
                }
            }
            return blob;
        } catch (Exception e){
            Logger.Error(ExceptionUtils.getStackTrace(e));
            ModLoader.ShowWarning("Error loading plugin, skipping...");
            return null;
        }
    }

    public static void LoadPlugin(PluginBlob blob) throws IOException {
        ClassLoader authorizedLoader = URLClassLoader.newInstance(new URL[] { blob.pluginFile.toPath().toUri().toURL() });
        SaveSystem.xstream.setClassLoader(authorizedLoader);
        ZipInputStream jarFile = new ZipInputStream(new FileInputStream(blob.pluginFile.getAbsoluteFile()));
        ZipEntry jarEntry;
        while (true) {
            jarEntry = jarFile.getNextEntry();
            if (jarEntry == null)
                break;
            if (jarEntry.getName().endsWith(".class")) {
                String classname = jarEntry.getName().replaceAll("/", "\\.");
                classname = classname.substring(0, classname.length() - 6);
                if (!classname.contains("$")) {
                    try {
                        final Class<?> myLoadedClass = Class.forName(classname, true, authorizedLoader);
                        if (Plugin.class.isAssignableFrom(myLoadedClass)) {
                            Plugin p = (Plugin) myLoadedClass.getConstructor().newInstance();
                            blob.core = p;
                            p.OnLoad();
                            Logger.Debug("Loaded %s", p.Id());
                        }
                        if (EventTrigger.class.isAssignableFrom(myLoadedClass)) {
                            EventTrigger p = (EventTrigger) myLoadedClass.getConstructor().newInstance();
                            blob.triggers.add(p);
                        }
                        if(ItemComponent.class.isAssignableFrom(myLoadedClass)){
                            Item.registeredComponents.add((Class<? extends ItemComponent>) myLoadedClass);
                        }
                        Logger.Log("Loaded: " + myLoadedClass.getName());
                        blob.classes.add(myLoadedClass);
                    } catch (Exception e) {
                        Logger.Error(ExceptionUtils.getStackTrace(e));
                    }
                }
            }
        }
    }

    public static void TriggerEvent(String name, Object... args){
        for(PluginBlob blob : blobs){
            for(EventTrigger trigger : blob.triggers){
                try {
                    Class[] argList = new Class[args.length];
                    for(int i = 0; i < args.length; i++){
                        argList[i] = args[i].getClass();
                    }

                    trigger.getClass().getMethod(name, argList).invoke(trigger, args);
                } catch (Exception e) {
                    Logger.Error(ExceptionUtils.getStackTrace(e));
                }
            }
        }
    }

}
