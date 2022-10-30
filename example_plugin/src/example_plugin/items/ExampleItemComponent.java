package example_plugin.items;

import com.deepcore.kleicreator.sdk.item.FieldName;
import com.deepcore.kleicreator.sdk.item.ItemComponent;

import java.util.ArrayList;
import java.util.List;

public class ExampleItemComponent implements ItemComponent {
    public String example_field = "";
    @FieldName(name="Another Example Field")
    public int another_example_field;

    @Override
    public List<String> ExportLines() {
        return new ArrayList<>();
    }
}
