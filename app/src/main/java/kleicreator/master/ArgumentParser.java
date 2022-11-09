package kleicreator.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArgumentParser {
    public static List<String> singleArguments = new ArrayList<>();
    public static Map<String, String> doubleArguments = new HashMap<>();

    public static void ParseArguments(String[] args){
        for(String arg : args){
            if(arg.contains("=")){
                String[] split = arg.split("=");
                doubleArguments.put(split[0], split[1]);
            }else{
                singleArguments.add(arg);
            }
        }
    }
}
