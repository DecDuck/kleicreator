package modloader.resources;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import constants.Constants;
import items.Item;
import logging.Logger;
import master.Master;
import modloader.ModLoader;
import modloader.classes.ResourceAnimation;
import modloader.classes.ResourceSpeech;
import modloader.classes.ResourceTexture;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.w3c.dom.Text;
import savesystem.SaveObject;
import speech.SpeechFile;

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
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
            if(r.Is(ResourceTexture.class)){
                ResourceTexture m = r.Get();
                if(m.displayUse == "Inventory Image"){
                    inventoryimages.add(r);
                }
                if(m.displayUse == "Mod Icon"){
                    modicons.add(r);
                }
                if(m.displayUse == "Character Portrait"){
                    characterportraits.add(r);
                }
                if(m.displayUse == "Map Icon"){
                    mapicons.add(r);
                }
            }

            if(r.Is(ResourceSpeech.class)){
                speeches.add(r);
            }
        }
    }

    public static void LoadAnimation(ResourceAnimation r){
        ResourceAnimation resource = new ResourceAnimation();
        resource.animFilePath = r.animFilePath;
        resources.add(resource);
    }

    public static void CreateAnimation(String filePath){
        ResourceAnimation resource = new ResourceAnimation();
        resource.animFilePath = filePath;
        resources.add(resource);
    }

    public static void LoadTexture(ResourceTexture texture){
        ResourceTexture resource = new ResourceTexture();

        resource.texPath = texture.texPath;
        resource.xmlPath = texture.xmlPath;

        if(texture.type == TextureLocation.InventoryImage){
            resource.filePath = "images/inventoryimages/";
            resource.displayUse = "Inventory Image";
        }else if(texture.type == TextureLocation.ModIcon){
            resource.filePath = "";
            resource.displayUse = "Mod Icon";
        }else if(texture.type == TextureLocation.Portrait){
            resource.filePath = "bigportrait/";
            resource.displayUse = "Character Portrait";
        }else if(texture.type == TextureLocation.MapIcon){
            resource.filePath = "images/map_icons/";
            resource.displayUse = "Map Icon";
        }
        resource.type = texture.type;

        resources.add(resource);
        Logger.Log("Loaded texture resource");
    }

    public static void CreateTexture(String texPath, String xmlPath, TextureLocation location){
        ResourceTexture resource = new ResourceTexture();

        resource.texPath = texPath;
        resource.xmlPath = xmlPath;
        resource.type = location;

        LoadTexture(resource);
    }

    public static void LoadResource(Resource r){
        if(r.Is(ResourceTexture.class)){
            LoadTexture(r.Get());
        }else if(r.Is(ResourceSpeech.class)){
            LoadSpeech(r.Get());
        }
    }

    public static void LoadSpeech(ResourceSpeech r){
        try {
            File f = new File(r.speechFile.filePath);
            if(!f.exists()){
                Logger.Error("Speech resource doesn't exist.");
                return;
            }
            String dat = Files.readString(f.toPath());
            ResourceSpeech m = new ResourceSpeech();
            m.speechFile = new SpeechFile();
            m.speechFile.filePath = r.speechFile.filePath;
            m.speechFile.resourceName = r.speechFile.resourceName;

            for(String line:dat.split("\\\n")){
                String[] parts = line.split("\\=");
                m.speechFile.speech.put(parts[0].strip(), parts[1].strip().replaceAll("\\\"", ""));
            }

            resources.set(resources.indexOf(r), m);
        } catch (Exception e) {
            Logger.Error(ExceptionUtils.getStackTrace(e));
            ModLoader.ShowWarning("Error loading resource: " + e.getLocalizedMessage());
        }
    }

    public static void CreateSpeech(String fileName){
        String fileLocation = Constants.FILE_LOCATION + "/speech/" + fileName.toLowerCase() + ".dat";
        try {
            new File(fileLocation).createNewFile();
            Files.writeString(Path.of(fileLocation), "CHARACTERS.GENERIC.DESCRIBE.EXAMPLE = \"Look! A cool new item\"");
        } catch (IOException e) {
            Logger.Error(ExceptionUtils.getStackTrace(e));
            return;
        }
        ResourceSpeech r = new ResourceSpeech();
        r.speechFile = new SpeechFile();
        r.speechFile.filePath = fileLocation;
        r.speechFile.resourceName = fileName;
        resources.add(r);
        LoadSpeech(r);
    }
}
