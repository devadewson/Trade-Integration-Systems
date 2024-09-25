package com.maybank.integratorapp.model.mq.accountinquiry.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Details {
    @JacksonXmlProperty(localName = "Error")
    private String error;
    @JacksonXmlProperty(localName = "Warning")

    private String warning;
    @JacksonXmlProperty(localName = "Info")

    private String info;

    public String getError() {
        return error;
    }

    public void setError(String value) {
        this.error = value;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String value) {
        this.warning = value;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String value) {
        this.info = value;
    }
}
