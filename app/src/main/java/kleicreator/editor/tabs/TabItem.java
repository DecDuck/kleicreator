package kleicreator.editor.tabs;

import kleicreator.editor.frames.TabItemForm;
import kleicreator.sdk.item.Item;

public class TabItem extends Tab {
    public Item item;
    private TabItemForm tabItemForm;
    public TabItem(Item item) {
        this.item = item;
        this.title = item.itemName;
        tabItemForm = new TabItemForm(this);
    }
}
