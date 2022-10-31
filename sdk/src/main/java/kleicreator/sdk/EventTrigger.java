package kleicreator.sdk;

import kleicreator.sdk.item.Item;

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

    default List<String> InjectItemLines(Item item){
        return new ArrayList<>();
    }

    default List<String> InjectModmainLines(){
        return new ArrayList<>();
    }

    default void OnExport(String exportPath) {}
}
