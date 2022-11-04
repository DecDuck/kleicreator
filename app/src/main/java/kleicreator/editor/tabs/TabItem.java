package kleicreator.editor.tabs;

import kleicreator.editor.frames.TabItemForm;
import kleicreator.sdk.item.Item;

public class TabItem extends Tab {
    public Item item;
    private final TabItemForm tabItemForm;

    public TabItem(Item item) {
        this.item = item;
        this.title = "Item: " + item.itemName;
        tabItemForm = new TabItemForm(this, item);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TabItem) {
            return ((TabItem) obj).item.id.equals(item.id);
        } else {
            return false;
        }
    }
}
