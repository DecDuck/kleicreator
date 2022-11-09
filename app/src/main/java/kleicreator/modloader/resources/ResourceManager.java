package kleicreator.modloader.resources;

import kleicreator.config.Config;
import kleicreator.constants.Constants;
import kleicreator.logging.Logger;
import kleicreator.speech.SpeechFile;
import kleicreator.modloader.ModLoader;
import kleicreator.modloader.classes.ResourceAnimation;
import kleicreator.modloader.classes.ResourceSpeech;
import kleicreator.modloader.classes.ResourceTexture;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ResourceManager {
    public static List<Resource> resources = new ArrayList<Resource>();

    public static List<Resource> inventoryimages = new ArrayList<Resource>();
    public static List<Resource> modicons = new ArrayList<Resource>();
    public static List<Resource> mapicons = new ArrayList<Resource>();
    public static List<Resource> speeches = new ArrayList<Resource>();

    public static void GenerateResourceLists() {
        inventoryimages.clear();
        modicons.clear();
        mapicons.clear();
        speeches.clear();

        for (Resource r : resources) {
            if (r.Is(ResourceTexture.class)) {
                ResourceTexture m = r.Get();
                if (m.type == TextureType.InventoryImage) {
                    inventoryimages.add(r);
                }
                if (m.type == TextureType.ModIcon) {
                    modicons.add(r);
                }
                if (m.type == TextureType.MapIcon) {
                    mapicons.add(r);
                }
            }
            else if (r.Is(ResourceSpeech.class)) {
                speeches.add(r);
            }
        }
    }

    public static void LoadAnimation(ResourceAnimation r) {
        ResourceAnimation resource = new ResourceAnimation();
        resource.animFilePath = r.animFilePath;
        resources.add(resource);
    }

    public static void CreateAnimation(String filePath) {
        ResourceAnimation resource = new ResourceAnimation();
        resource.animFilePath = filePath;
        resources.add(resource);
    }

    public static void LoadTexture(ResourceTexture texture) {
        ResourceTexture resource = new ResourceTexture();

        resource.texPath = texture.texPath;
        resource.xmlPath = texture.xmlPath;

        if (texture.type == TextureType.InventoryImage) {
            resource.filePath = "images/inventoryimages/";
            resource.displayUse = "Inventory Image";
        } else if (texture.type == TextureType.ModIcon) {
            resource.filePath = "";
            resource.displayUse = "Mod Icon";
        } else if (texture.type == TextureType.Portrait) {
            resource.filePath = "bigportrait/";
            resource.displayUse = "Character Portrait";
        } else if (texture.type == TextureType.MapIcon) {
            resource.filePath = "images/map_icons/";
            resource.displayUse = "Map Icon";
        }
        resource.type = texture.type;

        resources.add(resource);
        Logger.Debug("Loaded texture resource");
    }

    public static void CreateTexture(String texPath, String xmlPath, TextureType location) {
        ResourceTexture resource = new ResourceTexture();

        if((Boolean) Config.GetData("kleicreator.copyresources")){
            try{
                resource.texPath = String.valueOf(copyTo(Path.of(texPath), Constants.constants.KLEICREATOR_LOCATION +"/data/"));
                resource.xmlPath = String.valueOf(copyTo(Path.of(xmlPath), Constants.constants.KLEICREATOR_LOCATION +"/data/"));
            }catch (IOException e){
                ModLoader.ShowWarning("Failed to copy files, disabling \"Copy Resources\"");
                Logger.Error(e);
                Config.SaveData("kleicreator.copyresources", false);
                CreateTexture(texPath, xmlPath, location);
            }
        }else{
            resource.texPath = texPath;
            resource.xmlPath = xmlPath;
        }
        resource.type = location;

        LoadTexture(resource);
    }

    private static Path copyTo(Path in, String destination) throws IOException {
        Path out = Path.of(destination+in.getFileName());
        Files.copy(in, out);
        return out;
    }

    public static void LoadResource(Resource r) {
        if (r.Is(ResourceTexture.class)) {
            LoadTexture(r.Get());
        } else if (r.Is(ResourceSpeech.class)) {
            LoadSpeech(r.Get());
        }
    }

    public static void LoadSpeech(ResourceSpeech r) {
        try {
            File f = new File(r.speechFile.filePath);
            if (!f.exists()) {
                Logger.Error("Speech resource doesn't exist.");
                return;
            }
            String dat = Files.readString(f.toPath());
            ResourceSpeech m = new ResourceSpeech();
            m.speechFile = new SpeechFile();
            m.speechFile.filePath = r.speechFile.filePath;
            m.speechFile.resourceName = r.speechFile.resourceName;

            for (String line : dat.split("\\\n")) {
                String[] parts = line.split("\\=");
                m.speechFile.speech.put(parts[0].strip(), parts[1].strip().replaceAll("\\\"", ""));
            }

            resources.set(resources.indexOf(r), m);
        } catch (Exception e) {
            Logger.Error(e);
            ModLoader.ShowWarning("Error loading resource: " + e.getLocalizedMessage());
        }
    }

    public static void CreateSpeech(String fileName) {
        String fileLocation = Constants.constants.KLEICREATOR_LOCATION + "/speech/" + fileName.toLowerCase() + ".dat";
        try {
            new File(fileLocation).createNewFile();
            Files.writeString(Path.of(fileLocation), "CHARACTERS.GENERIC.DESCRIBE.EXAMPLE = \"Look! A cool new item\"");
        } catch (IOException e) {
            Logger.Error(e);
            JOptionPane.showMessageDialog(ModLoader.modEditorFrame, "Unable to create speech file!", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ResourceSpeech r = new ResourceSpeech();
        r.speechFile = new SpeechFile();
        r.speechFile.filePath = fileLocation;
        r.speechFile.resourceName = fileName;
        resources.add(r);
        LoadSpeech(r);
    }

    public enum TextureType {
        InventoryImage,
        ModIcon,
        Portrait,
        MapIcon
    }
}
