package com.deepcore.kleicreator.plugin;

import com.deepcore.kleicreator.constants.Constants;
import com.deepcore.kleicreator.logging.Logger;
import com.deepcore.kleicreator.modloader.ModLoader;
import com.deepcore.kleicreator.sdk.Plugin;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginHandler {

    public static Map<String,Plugin> plugins = new HashMap<>();

    public static void LoadPlugins(){
        plugins.clear();
        File folder = new File(Constants.FILE_LOCATION + "/plugins");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getAbsolutePath().endsWith(".jar")) {
                try {
                    LoadPlugin(listOfFiles[i]);
                } catch (Exception e) {
                    Logger.Error(ExceptionUtils.getStackTrace(e));
                }
            }
        }
    }

    public static void LoadPlugin(File pluginFile) throws ClassNotFoundException, InstantiationException, IllegalAccessException, MalformedURLException {
        ClassLoader authorizedLoader = URLClassLoader.newInstance(new URL[] { pluginFile.toURL() });
        String p = ModLoader.fileComponent(pluginFile.getAbsolutePath());
        Plugin plugin = (Plugin) authorizedLoader.loadClass( p.substring(0, p.length()-4)+ ".Plugin").newInstance();
        plugins.put(plugin.id(), plugin);
    }

    public static void TriggerEvent(String name, Object... args){
        for(Map.Entry<String, Plugin> m: plugins.entrySet()){
            try {
                Class[] argList = new Class[args.length];
                for(int i = 0; i < args.length; i++){
                    argList[i] = args[i].getClass();
                }

                m.getValue().getClass().getMethod("on"+name, argList).invoke(m.getValue(), args);
            } catch (Exception e) {
                Logger.Error(ExceptionUtils.getStackTrace(e));
            }
        }
    }

    public static <T> T TriggerEventForMod(String modId, String name, Object... args){
        Plugin p = plugins.get(modId);
        Class[] argList = new Class[args.length];
        for(int i = 0; i < args.length; i++){
            argList[i] = args[i].getClass();
        }

        try {
            return (T) p.getClass().getMethod("on"+name, argList).invoke(p, args);
        } catch (Exception e){
            Logger.Error(ExceptionUtils.getStackTrace(e));
            return null;
        }
    }
}
