package com.maybank.integratorapp.model.mq.facilitiesdetails.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.maybank.integratorapp.model.mq.facilities.response.FacilitiesResponse;

@JacksonXmlRootElement(localName = "ServiceResponse")
public class ServiceResponse {

    @JacksonXmlProperty(localName = "ResponseHeader")
    private ResponseHeader responseHeader;

    @JacksonXmlProperty(localName = "FacilityDetailsResponse", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private FacilityDetailsResponse facilityDetailsResponse;

    // Getters and setters
    public ServiceResponse(){
        ResponseHeader header = new ResponseHeader();
        header.setDetails(new Details());
        this.responseHeader = header;
        this.facilityDetailsResponse = new FacilityDetailsResponse();

    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public FacilityDetailsResponse getFacilityDetailsResponse() {
        return facilityDetailsResponse;
    }

    public void setFacilityDetailsResponse(FacilityDetailsResponse facilityDetailsResponse) {
        this.facilityDetailsResponse = facilityDetailsResponse;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }
}