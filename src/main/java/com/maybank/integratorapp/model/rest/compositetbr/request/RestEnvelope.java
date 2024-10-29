package com.maybank.integratorapp.model.rest.compositetbr.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

public class RestEnvelope {
    public RestEnvelope(){
        this.compositeTBR = new CompositeTBR();
    }
    @JsonProperty("CompositeTBR")
    private CompositeTBR compositeTBR;

    public CompositeTBR getCompositeTBR() {
        return compositeTBR;
    }

    public void setCompositeTBR(CompositeTBR compositeTBR) {
        this.compositeTBR = compositeTBR;
    }
}
