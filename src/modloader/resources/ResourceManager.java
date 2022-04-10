package modloader.resources;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.Dom4JDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import constants.Constants;
import logging.Logger;
import modloader.ModLoader;
import modloader.classes.Texture;
import savesystem.SaveObject;
import speech.CharacterSpeech;
import speech.ItemSpeech;
import speech.SpeechFile;

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

    public enum ResourceType{
        Texture,
        Speech
    }

    public static void CreateSpeechXStream(){
        speechXStream = new XStream(new DomDriver());
        speechXStream.alias("type", SpeechFile.SpeechType.class);
        speechXStream.alias("character", CharacterSpeech.class);
        speechXStream.alias("item", ItemSpeech.class);
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

    public static void LoadResource(String animPath){
        Resource resource = new Resource();
        resource.isAnim = true;
        resource.isSpeech = false;
        resource.isTexture = false;
        resource.animFilePath = animPath;

        resources.add(resource);
    }

    public static void LoadResource(String tex, String xml, TextureLocation texLocation){
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

    public static void LoadResource(SpeechFile.SpeechType speechType, String speechName){
        if(speechXStream == null){
            CreateSpeechXStream();
        }
        Resource r = new Resource();
        r.isTexture = false;
        r.isSpeech = true;
        r.isAnim = false;
        String fileLocation = Constants.FILE_LOCATION + "/speech/" + speechName.toLowerCase() + ".xml";
        if(speechType == SpeechFile.SpeechType.Character){
            r.speechFile = new SpeechFile(speechType, new CharacterSpeech());
            r.speechFile.characterSpeech.speech.put("DESCRIBE.EXAMPLE", "Look! A cool new item!");
        }else if(speechType == SpeechFile.SpeechType.Item){
            r.speechFile = new SpeechFile(speechType, new ItemSpeech());
            r.speechFile.itemSpeech.speech.put("DESCRIBE", "Look! A cool new item!");
            r.speechFile.itemSpeech.itemName = speechName;
        }
        r.speechFile.filePath = fileLocation;
        r.speechFile.resourceName = speechName.toLowerCase();

        try {
            new File(fileLocation).createNewFile();
            FileWriter fileWriter = new FileWriter(fileLocation);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(speechXStream.toXML(r));
            printWriter.close();
        } catch (IOException e) {
            Logger.Error(e.getLocalizedMessage());
        }

        resources.add(r);
        Logger.Log("Loaded speech resource");
    }

    public static void LoadResource(Resource r){
        Logger.Log("Directing resource");
        if(r.isTexture){
            LoadResource(r.texture.texPath, r.texture.xmlPath, r.texLocation);
        }else if(r.isSpeech){
            LoadResource(r.speechFile.speechType, r.speechFile.filePath);
        }
        Logger.Log("Directed");
    }

    public static void ReloadResource(Resource r){
        try {
            File f = new File(r.speechFile.filePath);
            String xml = Files.readString(f.toPath());

            resources.set(resources.indexOf(r), (Resource) speechXStream.fromXML(xml));
        } catch (FileNotFoundException e) {
            Logger.Error(e.getLocalizedMessage());
        } catch (IOException e) {
            Logger.Error(e.getLocalizedMessage());
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
