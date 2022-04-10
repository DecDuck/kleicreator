package speech;

import logging.Logger;

public class SpeechFile {

    public enum SpeechType{
        Character,
        Item
    }

    public String resourceName = "";
    public String filePath = "";

    public SpeechType speechType;

    public CharacterSpeech characterSpeech;
    public ItemSpeech itemSpeech;

    public SpeechFile(SpeechType s, Object speech){
        speechType = s;
        if(s == SpeechType.Character){
            characterSpeech = (CharacterSpeech) speech;
        }else if (s == SpeechType.Item){
            itemSpeech = (ItemSpeech) speech;
        }
        Logger.Log("Created speech object with settings:" + s + ";" + speech);
    }

}
