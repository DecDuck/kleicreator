package example_plugin;

import com.deepcore.kleicreator.sdk.EventTrigger;
import com.deepcore.kleicreator.sdk.logging.Logger;

public class ExampleTriggerHandler implements EventTrigger {
    @Override
    public void OnLoad() {
        Logger.Log("Loaded!");
    }
}
