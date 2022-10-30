package com.deepcore.kleicreator.export;

import com.deepcore.kleicreator.modloader.classes.ResourceSpeech;
import com.deepcore.kleicreator.modloader.resources.Resource;
import com.deepcore.kleicreator.modloader.resources.ResourceManager;

import java.util.List;
import java.util.Map;

public class SpeechExporter {
    public static final String DEFAULT_TEMPLATE = "STRINGS.$KEY$ = \"$VALUE$\"";

    public static String GenerateSpeech() {
        String output = "";
        List<Resource> speech = ResourceManager.speeches;
        for (Resource r : speech) {
            ResourceSpeech m = r.Get();
            for (Map.Entry<String, String> e : m.speechFile.speech.entrySet()) {
                output += DEFAULT_TEMPLATE.replace("$KEY$", e.getKey()).replace("$VALUE$", e.getValue());
            }
        }
        return output;
    }
}
