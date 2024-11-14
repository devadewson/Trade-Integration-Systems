package com.maybank.integratorapp.model.mq.facilities.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ExtraDataFieldss {
    @JacksonXmlProperty(localName = "ExtraDataFields")
    private ExtraDataFields extraDataFields;

    public ExtraDataFields getExtraDataFields() {
        return extraDataFields;
    }

    public void setExtraDataFields(ExtraDataFields extraDataFields) {
        this.extraDataFields = extraDataFields;
    }
}
