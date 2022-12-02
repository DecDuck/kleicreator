package kleicreator.export.interfaces;

import kleicreator.export.Exporter;
import kleicreator.util.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public interface Job {

    default String loadTemplate(String path){
        try {
            return new Scanner(
                    ClassLoader.getSystemClassLoader().getResource("templates/"+path).openStream(),
                    "UTF-8")
                    .useDelimiter("\\A")
                    .next();
        } catch (IOException e) {
            Logger.Error(e);
            return "";
        }
    }

    default String sub(String template, String key, String value){
        return template.replaceAll(Pattern.quote(key), value);
    }

    Map<String, String> run(Exporter exporter) throws Exception;

    String prettyName();
}
