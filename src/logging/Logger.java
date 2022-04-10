package logging;
import modloader.ModLoader;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

import static constants.Constants.FILE_LOCATION;

public class Logger {

    public static String logLocation = FILE_LOCATION + "/log.txt";

    public static String currentLog = "";

    public static void Log(String message){
        String currentMessage = "[" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + "] " + message + "\n";
        currentLog = currentLog + currentMessage;
        System.out.print(currentMessage);
        WriteChanges();
    }

    public static void Warn(String warning){
        Log("[WARN] " + warning);
        WriteChanges();
    }

    public static void Error(String error){
        Log("[ERROR] " + error);
        WriteChanges();
    }

    public static void WriteChanges(){
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
