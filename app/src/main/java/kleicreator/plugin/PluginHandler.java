package kleicreator.plugin;

import kleicreator.sdk.constants.Constants;
import kleicreator.items.Item;
import kleicreator.sdk.EventTrigger;
import kleicreator.sdk.Plugin;
import kleicreator.sdk.item.ItemComponent;
import kleicreator.sdk.logging.Logger;
import org.apache.commons.lang3.exception.ExceptionUtils;
import kleicreator.savesystem.SaveSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PluginHandler {

    public static Map<String,Plugin> plugins = new HashMap<>();
    public static List<EventTrigger> eventTriggers = new ArrayList<>();

    public static void LoadPlugins(){
        eventTriggers.clear();
        try {
            String[] jarFiles = new File(Constants.FILE_LOCATION + "/plugins/").list();
            if (jarFiles.length > 0){
                for(String jarLocation : jarFiles){
                    LoadPlugin(new File(Constants.FILE_LOCATION + "/plugins/" + jarLocation));
                }
            }

        } catch (Exception e) {
            Logger.Error(ExceptionUtils.getStackTrace(e));
        }
    }

    public static void LoadPlugin(File pluginFile) throws IOException {
        ClassLoader authorizedLoader = URLClassLoader.newInstance(new URL[] { pluginFile.toPath().toUri().toURL() });
        SaveSystem.xstream.setClassLoader(authorizedLoader);
        ZipInputStream jarFile = new ZipInputStream(new FileInputStream(pluginFile.getAbsoluteFile()));
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
                            plugins.put(p.Id(), p);
                            p.OnLoad();
                            Logger.Debug("Loaded %s", p.Id());
                            continue;
                        }
                        if (EventTrigger.class.isAssignableFrom(myLoadedClass)) {
                            EventTrigger p = (EventTrigger) myLoadedClass.getConstructor().newInstance();
                            eventTriggers.add(p);
                            continue;
                        }
                        if(ItemComponent.class.isAssignableFrom(myLoadedClass)){
                            Item.registeredComponents.add((Class<? extends ItemComponent>) myLoadedClass);
                            continue;
                        }
                    } catch (Exception e) {
                        Logger.Error(ExceptionUtils.getStackTrace(e));
                    }
                }
            }
        }
    }

    public static void TriggerEvent(String name, Object... args){
        for(EventTrigger trigger: eventTriggers){
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
