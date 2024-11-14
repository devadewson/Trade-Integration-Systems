package com.maybank.integratorapp.model.mq.facilities.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.maybank.integratorapp.model.mq.accountinquiry.request.AvailBalRequest;
@JacksonXmlRootElement(localName = "ServiceRequest", namespace = "urn:control.services.tiplus2.misys.com")

public class ServiceRequest {
    @JacksonXmlProperty(localName = "RequestHeader")
    private RequestHeader requestHeader;
    @JacksonXmlProperty(localName = "FacilitiesRequest", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private FacilitiesRequest facilitiesRequest;

    public RequestHeader getRequestHeader() { return requestHeader; }
    public void setRequestHeader(RequestHeader value) { this.requestHeader = value; }
    public FacilitiesRequest getFacilitiesRequest() { return facilitiesRequest; }
    public void setFacilitiesRequest(FacilitiesRequest value) { this.facilitiesRequest = value; }

}

