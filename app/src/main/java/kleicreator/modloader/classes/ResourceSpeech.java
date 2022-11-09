package kleicreator.modloader.classes;

import kleicreator.speech.SpeechFile;
import kleicreator.modloader.resources.Resource;

public class ResourceSpeech extends Resource {
    public SpeechFile speechFile;

    @Override
    public String toString() {
        return String.format("Speech (%s)", speechFile);
    }
}
