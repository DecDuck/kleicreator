package speech;

import java.util.HashMap;
import java.util.Map;

public class SpeechFile {

    public String resourceName = "";
    public String filePath = "";
    public Map<String, String> speech = new HashMap<String, String>();

    public SpeechFile() {

    }

    public enum SpeechType {
        Character,
        Item
    }

}
