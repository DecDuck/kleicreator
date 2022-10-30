package com.deepcore.kleicreator.sdk;

import javax.swing.*;

public interface EventTrigger {
    default void OnLoad(){

    }

    default void OnConfigSetup(JFrame configFrame){

    }

    default void OnConfigSave(JFrame configFrame){

    }

    default void OnStartup(){

    }
}
