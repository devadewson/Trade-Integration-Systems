package com.maybank.integratorapp.model.mq.reservation.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.maybank.integratorapp.model.mq.customersearch.response.CustomerSearchResult;

import java.util.ArrayList;
import java.util.List;

public class ExtraDataFieldss {

    public ExtraDataFieldss() { this.extraDataFields = new ArrayList<ExtraDataFields>();}

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "ExtraDataFields",namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private List<ExtraDataFields> extraDataFields;

    public List<ExtraDataFields> getExtraDataFields() {
        return extraDataFields;
    }

    public void setExtraDataFields(List<ExtraDataFields> extraDataFields) {
        this.extraDataFields = extraDataFields;
    }
}
