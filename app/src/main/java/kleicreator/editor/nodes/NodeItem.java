package kleicreator.editor.nodes;

import kleicreator.items.Item;

public class NodeItem extends Node {
    public Item item;

    public NodeItem (Item item, Object userObject){
        super(userObject);
        this.item = item;
    }
}
