package modloader.resources;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import constants.Constants;
import items.Item;
import logging.Logger;
import modloader.ModLoader;
import modloader.classes.Texture;
import org.apache.commons.lang3.exception.ExceptionUtils;
import savesystem.SaveObject;
import speech.SpeechFile;

import javax.swing.*;
import java.nio.file.Files;
import java.util.*;
import java.io.*;

public class ResourceManager {
    public static List<Resource> resources = new ArrayList<Resource>();

    public static List<Resource> inventoryimages = new ArrayList<Resource>();
    public static List<Resource> modicons = new ArrayList<Resource>();
    public static List<Resource> characterportraits = new ArrayList<Resource>();
    public static List<Resource> mapicons = new ArrayList<Resource>();
    public static List<Resource> speeches = new ArrayList<Resource>();

    public static XStream speechXStream;

    public enum TextureLocation{
        InventoryImage,
        ModIcon,
        Portrait,
        MapIcon
    }

    public static void CreateSpeechXStream(){
        speechXStream = new XStream(new DomDriver());
        speechXStream.alias("type", SpeechFile.SpeechType.class);
        speechXStream.alias("resource", Resource.class);
        speechXStream.alias("project", SaveObject.class);
        speechXStream.alias("item", Item.class);
        speechXStream.alias("resource", Resource.class);

        Logger.Log("Created speechXStream with appropriate settings");
    }

    public static void GenerateResourceLists(){
        inventoryimages.clear();
        modicons.clear();
        characterportraits.clear();
        mapicons.clear();
        speeches.clear();

        for(Resource r:resources){
            if(r.displayUse == "Inventory Image"){
                inventoryimages.add(r);
            }
            if(r.displayUse == "Mod Icon"){
                modicons.add(r);
            }
            if(r.displayUse == "Character Portrait"){
                characterportraits.add(r);
            }
            if(r.displayUse == "Map Icon"){
                mapicons.add(r);
            }
            if(r.isSpeech){
                speeches.add(r);
            }
        }
    }

    public static void CreateResource(String animPath){
        Resource resource = new Resource();
        resource.isAnim = true;
        resource.isSpeech = false;
        resource.isTexture = false;
        resource.animFilePath = animPath;

        resources.add(resource);
    }

    public static void CreateResource(String tex, String xml, TextureLocation texLocation){
        Resource resource = new Resource();
        resource.isTexture = true;
        resource.isSpeech = false;
        resource.isAnim = false;

        resource.texture = new Texture();
        resource.texture.texPath = tex;
        resource.texture.xmlPath = xml;

        if(texLocation == TextureLocation.InventoryImage){
            resource.filePath = "images/inventoryimages/";
            resource.displayUse = "Inventory Image";
        }else if(texLocation == TextureLocation.ModIcon){
            resource.filePath = "";
            resource.displayUse = "Mod Icon";
        }else if(texLocation == TextureLocation.Portrait){
            resource.filePath = "bigportrait/";
            resource.displayUse = "Character Portrait";
        }else if(texLocation == TextureLocation.MapIcon){
            resource.filePath = "images/map_icons/";
            resource.displayUse = "Map Icon";
        }
        resource.texLocation = texLocation;

        resources.add(resource);
        Logger.Log("Loaded texture resource");
    }

    public static void CreateResource(SpeechFile.SpeechType speechType, String speechName){
        if(speechXStream == null){
            CreateSpeechXStream();
        }
        Resource r = new Resource();
        r.isTexture = false;
        r.isSpeech = true;
        r.isAnim = false;
        String fileLocation = Constants.FILE_LOCATION + "/speech/" + speechName.toLowerCase() + ".dat";
        r.speechFile = new SpeechFile();
        r.speechFile.speech.put("CHARACTERS.GENERIC.DESCRIBE.EXAMPLE", "Look! A cool new item!");
        r.speechFile.filePath = fileLocation;
        r.speechFile.resourceName = speechName.toLowerCase();

        try {

            File f = new File(fileLocation);
            if(f.exists()){
                JOptionPane.showMessageDialog(ModLoader.modEditorFrame, "Speech file already exists!", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                f.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(fileLocation);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            String fileContents = "";
            for(Map.Entry<String, String> e:r.speechFile.speech.entrySet()){
                fileContents += String.format("%s=%s", e.getKey(), e.getValue());
            }
            printWriter.print(fileContents);
            printWriter.close();
        } catch (IOException e) {
            Logger.Error(ExceptionUtils.getStackTrace(e));
        }

        resources.add(r);
        Logger.Log("Loaded speech resource");
    }

    public static void CreateResource(Resource r){
        Logger.Log("Directing resource");
        if(r.isTexture){
            CreateResource(r.texture.texPath, r.texture.xmlPath, r.texLocation);
        }else if(r.isSpeech){
            CreateResource(SpeechFile.SpeechType.Item, r.speechFile.resourceName);
        }
        Logger.Log("Directed");
    }

    public static void LoadResource(Resource r){
        if(r.isTexture){
            CreateResource(r.texture.texPath, r.texture.xmlPath, r.texLocation);
        }else{
            resources.add(r);
            ReloadSpeechResource(r);
        }
    }

    public static void ReloadSpeechResource(Resource r){
        try {
            File f = new File(r.speechFile.filePath);
            String dat = Files.readString(f.toPath());
            Resource m = new Resource();
            m.isSpeech = true;
            m.speechFile = new SpeechFile();
            m.speechFile.filePath = r.speechFile.filePath;
            m.speechFile.resourceName = r.speechFile.resourceName;
            m.filePath = r.filePath;

            for(String line:dat.split("\\\n")){
                String[] parts = line.split("\\=");
                m.speechFile.speech.put(parts[0], parts[1]);
            }

            resources.set(resources.indexOf(r), m);
        } catch (Exception e) {
            Logger.Error(ExceptionUtils.getStackTrace(e));
            ModLoader.ShowWarning("Error loading resource: " + e.getLocalizedMessage());
        }
    }

    public static Resource GetResource(String name){
        for(int i = 0; i < resources.size(); i++){
            if(resources.get(i).texture.texPath == name || new File(resources.get(i).texture.texPath).getName() == name){
                return resources.get(i);
            }
        }
        return null;
    }

    public static void RemoveResource(String type, String feature){
        if(type == "Speech"){
            for(Resource r:resources){
                if(r.speechFile.filePath == feature){
                    resources.remove(r);
                }
            }
        }else if(type == "Texture"){
            for(Resource r:resources){
                if(r.texture.texPath + ";" + r.texture.xmlPath == feature){
                    resources.remove(r);
                }
            }
        }
    }
}
