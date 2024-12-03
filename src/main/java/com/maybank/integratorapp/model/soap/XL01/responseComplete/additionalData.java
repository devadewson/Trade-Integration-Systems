package com.maybank.integratorapp.model.soap.XL01.responseComplete;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "additionalData")
public class additionalData {
    @JacksonXmlProperty(isAttribute = true )
    private String param;

    @JacksonXmlText
    private String value;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public additionalData( ) {
        this.param = param;
        this.value = value;
    }
}
