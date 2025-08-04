package com.maybank.integratorapp.data.utils;

import java.lang.reflect.Type;

public class FieldMetadata {
    private String name;
    private String displayName;
    private Class<?> type;
    private boolean required;

    public FieldMetadata(String name, String displayName, Class<?> stringClass, boolean b) {
        this.setName(name);
        this.setDisplayName(displayName);
        this.setType(stringClass);
        this.setRequired(b);
    }

    // constructor, getters, setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
