package speech;

import logging.Logger;

import java.util.HashMap;
import java.util.Map;

public class SpeechFile {

    public enum SpeechType{
        Character,
        Item
    }

    public String resourceName = "";
    public String filePath = "";

    public Map<String, String> speech = new HashMap<String, String>();

    public SpeechFile(){

    }

}
