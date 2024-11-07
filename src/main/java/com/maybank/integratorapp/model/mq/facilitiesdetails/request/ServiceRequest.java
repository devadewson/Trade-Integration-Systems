package com.maybank.integratorapp.model.mq.facilitiesdetails.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.maybank.integratorapp.model.mq.facilities.request.FacilitiesRequest;

@JacksonXmlRootElement(localName = "ServiceRequest", namespace = "urn:control.services.tiplus2.misys.com")

public class ServiceRequest {
    @JacksonXmlProperty(localName = "RequestHeader")
    private RequestHeader requestHeader;
    @JacksonXmlProperty(localName = "FacilityDetailsRequest", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private FacilityDetailsRequest facilityDetailsRequest;

    public RequestHeader getRequestHeader() { return requestHeader; }
    public void setRequestHeader(RequestHeader value) { this.requestHeader = value; }
    public FacilityDetailsRequest getFacilitiesDetailsRequest() { return facilityDetailsRequest; }
    public void setFacilityDetailsRequest(FacilityDetailsRequest value) { this.facilityDetailsRequest = value; }

}

