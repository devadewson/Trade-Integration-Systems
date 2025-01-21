package com.maybank.integratorapp.model.soap.fcclimit.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "OFA")
public class OFAResponse {
    @JacksonXmlProperty(localName = "Limit")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Limit> limit;

    public List<Limit> getLimit() {
        return limit;
    }

    public void setLimit(List<Limit> limit) {
        this.limit = limit;
    }
}