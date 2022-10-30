package com.deepcore.kleicreator.sdk;

import com.deepcore.kleicreator.master.Master;
import com.deepcore.kleicreator.master.Starter;

public class Application {

    public static void Restart(){
        Starter.startCounter++;
        Master.exit = true;
    }

    public static void Exit(){
        System.exit(0);
    }

}
