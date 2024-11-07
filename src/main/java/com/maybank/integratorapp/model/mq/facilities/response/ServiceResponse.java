package com.maybank.integratorapp.model.mq.facilities.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "ServiceResponse")
public class ServiceResponse {

    @JacksonXmlProperty(localName = "ResponseHeader")
    private ResponseHeader responseHeader;

    @JacksonXmlProperty(localName = "FacilitiesResponse", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private FacilitiesResponse facilitiesResponse;

    // Getters and setters
    public ServiceResponse(){
        ResponseHeader header = new ResponseHeader();
        header.setDetails(new Details());
        this.responseHeader = header;
        this.facilitiesResponse = new FacilitiesResponse();

    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public FacilitiesResponse getFacilitiesResponse() {
        return facilitiesResponse;
    }

    public void setFacilitiesResponse(FacilitiesResponse facilitiesResponse) {
        this.facilitiesResponse = facilitiesResponse;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }
}