package kleicreator.export.jobs;

import kleicreator.export.interfaces.Job;
import kleicreator.export.Exporter;
import kleicreator.resources.types.ResourceSpeech;
import kleicreator.resources.Resource;
import kleicreator.resources.ResourceManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpeechJob implements Job {
    private static final String DEFAULT_TEMPLATE = "STRINGS.$KEY$ = \"$VALUE$\"";

    @Override
    public Map<String, String> run(Exporter exporter) {
        StringBuilder output = new StringBuilder();
        List<Resource> speech = ResourceManager.speeches;
        for (Resource r : speech) {
            ResourceSpeech m = r.Get();
            for (Map.Entry<String, String> e : m.speechFile.speech.entrySet()) {
                output.append(DEFAULT_TEMPLATE.replace("$KEY$", e.getKey()).replace("$VALUE$", e.getValue()));
            }
        }
        HashMap<String, String> exposed = new HashMap<>();
        exposed.put("speech", output.toString());
        return exposed;
    }

    @Override
    public String prettyName() {
        return "Speech Job";
    }
}
