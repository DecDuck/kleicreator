package logging;
import modloader.ModLoader;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static constants.Constants.FILE_LOCATION;

public class Logger {

    public static String logLocation = FILE_LOCATION + "/log.txt";

    public static String currentLog = "";

    public static Date startTime;

    public static void Start(){
        startTime = Calendar.getInstance().getTime();
    }

    public static void Log(String message){
        long time = Calendar.getInstance().getTime().getTime() - startTime.getTime();
        String currentMessage = "[" + String.format("%08d", time) + "] " + message + "\n";
        currentLog = currentLog + currentMessage;
        System.out.print(currentMessage);
        WriteChanges();
    }

    public static void Log(String format, String... parts){
        Log(String.format(format, parts));
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
