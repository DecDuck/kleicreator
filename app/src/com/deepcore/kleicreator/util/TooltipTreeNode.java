package com.deepcore.kleicreator.util;

import javax.swing.tree.DefaultMutableTreeNode;


public class TooltipTreeNode extends DefaultMutableTreeNode implements Tooltippable {
    private String shortName;
    private String longName;

    public TooltipTreeNode(String shortName, String longName) {
        this.shortName = shortName;
        this.longName = longName;
    }

    @Override
    public String getToolTip() {
        return longName;
    }

    @Override
    public String toString(){
        return shortName;
    }
}
