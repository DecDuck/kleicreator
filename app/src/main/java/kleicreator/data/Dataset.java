package kleicreator.data;

import java.util.HashMap;
import java.util.Map;

public class Dataset {
    public Map<String, Object> values;

    public void CreateEmpty(){
        values = new HashMap<>();
    }
}