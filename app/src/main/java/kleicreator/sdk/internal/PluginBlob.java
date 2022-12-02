package kleicreator.sdk.internal;

import kleicreator.sdk.EventTrigger;
import kleicreator.sdk.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PluginBlob implements Comparable<PluginBlob> {
    public Plugin core;
    public PluginMetadata metadata = new PluginMetadata();
    public File pluginFile;
    public List<Class> classes = new ArrayList<>();
    public List<EventTrigger> triggers = new ArrayList<>();

    @Override
    public int compareTo(PluginBlob o) {
        if(metadata == null || o.metadata == null){
            return 0;
        }
        if(metadata.loadorder > o.metadata.loadorder){
            return -1;
        }if(metadata.loadorder == o.metadata.loadorder){
            return 0;
        } else{
            return 1;
        }
    }
}
