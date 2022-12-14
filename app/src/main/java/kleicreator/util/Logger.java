package kleicreator.util;

import kleicreator.data.Constants;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class Logger {
    public enum Level {
        Debug,
        Info,
        Warn,
        Error
    }

    public static String logLocation = Constants.KLEICREATOR_LOCATION + "/log.txt";

    public static String currentLog = "";

    public static Date startTime;

    public static Level MinimumLogLevel = Level.Info;

    public static void Start() {
        startTime = Calendar.getInstance().getTime();
        if(ArgumentParser.doubleArguments.containsKey("--log")){
            MinimumLogLevel = Enum.valueOf(Level.class, ArgumentParser.doubleArguments.get("--log"));
        }
    }

    public static void Log(String message){
        Print(Level.Info, message);
    }

    public static void Log(String format, String... parts){
        Print(Level.Info, format, parts);
    }

    public static void Debug(String message){
        Print(Level.Debug, message);
    }

    public static void Debug(String format, String... parts){
        Print(Level.Debug, format, parts);
    }

    private static void Print(Level level, String message) {
        long time = Calendar.getInstance().getTime().getTime() - startTime.getTime();
        String currentMessage = "[" + String.format("%08d", time) + "] [" + level.toString().toUpperCase() + "] ["+ getCallerClassName()+"] " + message + "\n";
        currentLog = currentLog + currentMessage;
        if(level.ordinal() < MinimumLogLevel.ordinal()){

        }else{
            System.out.print(currentMessage);
        }
        WriteChanges();
    }

    private static String getCallerClassName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(Logger.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0) {
                return ste.getFileName().split("[.]")[0];
            }
        }
        return null;
    }

    public static void Print(Level level, String format, Object... parts) {
        Print(level, String.format(format, parts));
    }

    public static void Warn(String warning) {
        Print(Level.Warn, warning);
    }

    public static void Error(String error) {
        Print(Level.Error, error);
    }
    public static void Error(Throwable error) {
        Print(Level.Error, ExceptionUtils.getStackTrace(error));
    }


    public static void WriteChanges() {
        try {
            File file = new File(logLocation);
            FileWriter fr = new FileWriter(file, false);
            fr.write(currentLog);
            fr.close();
        } catch (IOException e) {
            try {
                new File(logLocation).createNewFile();
            } catch (IOException ioException) {
                return;
            }
            WriteChanges();
        }

    }

}
