package kleicreator.savesystem;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import kleicreator.sdk.logging.Logger;

import java.io.*;

public class SaveSystem {

    public static XStream xstream = new XStream(new StaxDriver());

    public static void ClearFile(String fileName) {
        try {
            FileWriter fwOb = new FileWriter(fileName, false);
            PrintWriter pwOb = new PrintWriter(fwOb, false);
            pwOb.flush();
            pwOb.close();
            fwOb.close();
        } catch (IOException e) {
            Logger.Error(e);
        }
    }

    public static void Save(String filePath) {
        Logger.Log("Starting save");
        try {
            File f = new File(filePath);
            if (!f.isFile()) {
                f.createNewFile();
            }

            ClearFile(filePath);

            SaveObject toSave = new SaveObject();

            String xml = xstream.toXML(toSave);

            FileWriter fileWriter = new FileWriter(filePath);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(xml);
            printWriter.close();

        } catch (IOException e) {
            Logger.Error(e);
            return;
        }
        Logger.Log("Completed save");
    }

    public static void Load(String filePath) {
        try {
            File f = new File(filePath);
            FileInputStream fis = new FileInputStream(f);
            byte[] data = new byte[(int) f.length()];
            fis.read(data);
            fis.close();

            String xml = new String(data);

            SaveObject e = (SaveObject) xstream.fromXML(xml);

            e.LoadBack();

            Logger.Log("Loaded project");
        } catch (IOException ioException) {
            Logger.Error(ioException);
        }
    }

    public static SaveObject TempLoad(String filePath) {
        try {
            File f = new File(filePath);
            FileInputStream fis = new FileInputStream(f);
            byte[] data = new byte[(int) f.length()];
            fis.read(data);
            fis.close();

            String xml = new String(data);

            SaveObject e = (SaveObject) xstream.fromXML(xml);
            return e;
        } catch (IOException ioException) {
            Logger.Error(ioException);
            return null;
        }
    }
}
