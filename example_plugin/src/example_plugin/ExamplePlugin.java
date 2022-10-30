package example_plugin;

import com.deepcore.kleicreator.sdk.Plugin;

public class ExamplePlugin implements Plugin {
    @Override
    public String Name() {
        return "Example Plugin";
    }

    @Override
    public String Author() {
        return "DeepCore";
    }
}
