package com.deepcore.kleicreator.sdk;

import com.deepcore.kleicreator.config.Config;
import com.deepcore.kleicreator.sdk.logger.Logger;

import javax.swing.*;

public interface Plugin {
    Logger logger = new Logger();

    default String name(){
        return this.getClass().getSimpleName();
    }
    default String id(){
        return this.getClass().getSimpleName().toLowerCase();
    }
    default String author(){
        return "";
    }

    default void onload(){
       logger.plugin = this;
    }

    default void onconfigdialogsetup(JFrame configFrame){

    }

    default void onconfigdialogsave(JFrame configFrame){

    }

    default void onstartup(){

    }

    default void onmodload(){

    }

    default void onmodeditorupdate(){

    }
}
