package com.deepcore.kleicreator.sdk;

import com.deepcore.kleicreator.config.Config;

import javax.swing.*;

public interface Plugin {
    default String Name(){
        return this.getClass().getSimpleName();
    }
    default String Id(){
        return this.getClass().getSimpleName().toLowerCase();
    }
    default String Author(){
        return "";
    }

    default void OnLoad(){

    }
}
