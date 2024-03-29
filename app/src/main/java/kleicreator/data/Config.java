package kleicreator.data;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import kleicreator.util.Logger;

import java.io.*;
import java.nio.file.Files;

public class Config {

    public static XStream xstream = new XStream(new DomDriver());

    static {
        xstream.addPermission(AnyTypePermission.ANY);
    }

    public static String GetFilePath(String datasetName){
        return Constants.GetConfigDirectory() + datasetName + ".xml";
    }

    public static boolean AssertDataset(String datasetName){
        String filePath = GetFilePath( datasetName );
        File file = new File(filePath);
        if(file.exists()){
            return true;
        }else{
            Dataset data = new Dataset();
            data.CreateEmpty();
            try {
                file.createNewFile();
                Files.writeString(file.toPath(),
                        xstream.toXML(data));
                return true;
            } catch (IOException e) {
                Logger.Error("Failed to initialize database, check for read/write permissions");
                return false;
            }
        }
    }

    public static boolean SaveData(String dataLocation, Object data){
        return SaveData(dataLocation, data, true);
    }

    public static boolean SaveData(String dataLocation, Object data, boolean replace){
        String[] dataLocationParts = dataLocation.split("[.]");
        String datasetName = dataLocationParts[0];
        String dataName = dataLocationParts[1];
        String filePath = GetFilePath( datasetName );
        File file = new File(filePath);
        Dataset dataset = (Dataset) xstream.fromXML(file);

        if(replace){
            dataset.values.put(dataName, data);
        }else{
            dataset.values.putIfAbsent(dataName, data);
        }

        try {
            xstream.toXML(dataset, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            Logger.Error("Failed writing to database, check for read/write permissions");
            return false;
        }
        return true;
    }

    public static Object GetData(String dataLocation){
        String[] dataLocationParts = dataLocation.split("[.]");
        String datasetName = dataLocationParts[0];
        String dataName = dataLocationParts[1];
        String filePath = GetFilePath( datasetName );
        File file = new File(filePath);
        Dataset dataset = (Dataset) xstream.fromXML(file);
        return dataset.values.get(dataName);
    }

}
