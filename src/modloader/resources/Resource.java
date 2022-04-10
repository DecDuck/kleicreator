package modloader.resources;

import modloader.classes.Texture;
import speech.SpeechFile;

public class Resource {
    public boolean isTexture;
    public boolean isSpeech;
    public boolean isAnim;

    //TEXTURE
    public Texture texture;
    public String filePath;
    public String displayUse;
    public ResourceManager.TextureLocation texLocation;

    //SPEECH
    public SpeechFile speechFile;

    //ANIMATION
    public String animFilePath;
}
