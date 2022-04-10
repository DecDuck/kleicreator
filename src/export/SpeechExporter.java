package export;

import modloader.resources.Resource;
import modloader.resources.ResourceManager;

import java.util.List;

public class SpeechExporter {
    public static final String DEFAULT_TEMPLATE = "STRINGS.$KEY$ = $VALUE$";

    public static String GenerateSpeech(){
        String output = "";
        List<Resource> speech = ResourceManager.speeches;
        for(Resource r:speech){

        }
        return output;
    }
}
