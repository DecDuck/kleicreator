package com.deepcore.kleicreator.sdk.logger;

import com.deepcore.kleicreator.sdk.Plugin;

import java.util.Calendar;

public class Logger {

    public Plugin plugin;

    public void Log(String message) {
        com.deepcore.kleicreator.logging.Logger.Log("[%s] %s", plugin.name(), message);
    }

    public void Log(String format, String... parts) {
        Log(String.format(format, parts));
    }

    public void Warn(String warning) {
        com.deepcore.kleicreator.logging.Logger.Warn(warning);
    }

    public void Error(String error) {
        com.deepcore.kleicreator.logging.Logger.Error(error);
    }
}
