package kleicreator.sdk;

import kleicreator.export.interfaces.Job;
import kleicreator.items.Item;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public interface EventTrigger {
    default void OnLoad(){

    }

    default void OnConfigSetup(JFrame configFrame){

    }

    default void OnConfigSave(JFrame configFrame){

    }

    default void OnStartup(){

    }

    default void OnModEditorUpdate(){

    }

    default void OnModLoad(){

    }

    default List<Job> ExportJobs() {return new ArrayList<>();}
}
