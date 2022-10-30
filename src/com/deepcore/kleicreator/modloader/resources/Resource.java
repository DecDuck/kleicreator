package com.deepcore.kleicreator.modloader.resources;

public class Resource {
    public <T> boolean Is(Class<T> t) {
        try {
            t.cast(this);
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public <T extends Resource> T Get() {
        return (T) this;
    }
}
