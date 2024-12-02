package com.maybank.integratorapp.model.soap.limit.XLBT.response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;


@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "additionalData")
public class AdditionalData {
    @JacksonXmlProperty(isAttribute = true)
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

    public AdditionalData() {
        this.param = param;
        this.value = value;
    }
}
