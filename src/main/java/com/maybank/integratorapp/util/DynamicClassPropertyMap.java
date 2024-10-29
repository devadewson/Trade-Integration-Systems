package com.maybank.integratorapp.util;

public class DynamicClassPropertyMap {
    public DynamicClassPropertyMap(String fieldName,String fieldType){
        this.FieldName = fieldName;
        this.FieldType = fieldType;
    }
    private String FieldName;
    private String FieldType;

    public String getFieldName() {
        return FieldName;
    }

    public void setFieldName(String fieldName) {
        FieldName = fieldName;
    }

    public String getFieldType() {
        return FieldType;
    }

    public void setFieldType(String fieldType) {
        FieldType = fieldType;
    }
}
