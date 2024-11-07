package com.maybank.integratorapp.model.mq.facilitiesdetails.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FacilityExtraDetails {
    @JacksonXmlProperty(localName = "ExtraDataKey")
    private String ExtraDataKey;
    @JacksonXmlProperty(localName = "Name")
    private String Name;
    @JacksonXmlProperty(localName = "Value")
    private String Value;

    public String getExtraDataKey() {
        return ExtraDataKey;
    }

    public void setExtraDataKey(String extraDataKey) {
        ExtraDataKey = extraDataKey;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
