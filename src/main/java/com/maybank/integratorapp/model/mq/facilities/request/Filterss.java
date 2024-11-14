package com.maybank.integratorapp.model.mq.facilities.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Filterss {
    @JacksonXmlProperty(localName = "Filters")
    private Filters filters;

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }
}
