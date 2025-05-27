package com.maybank.integratorapp.model.mq.accountinquiry.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.maybank.integratorapp.model.mq.accountinquiry.response.AvailBalResponse;
import com.maybank.integratorapp.model.mq.accountinquiry.response.ResponseHeader;

@JacksonXmlRootElement(localName = "ServiceResponse",namespace = "urn:control.services.tiplus2.misys.com")
public class ServiceResponse {

    @JacksonXmlProperty(namespace = "urn:control.services.tiplus2.misys.com",localName = "ResponseHeader")
    private ResponseHeader responseHeader;

    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "AvailBalResponse")
    private AvailBalResponse availBalResponse;

    // Getters and setters
    public ServiceResponse(){
        ResponseHeader header = new ResponseHeader();
        header.setDetails(new Details());
        this.responseHeader = header;
        this.availBalResponse = new AvailBalResponse();

    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public AvailBalResponse getAvailBalResponse() {
        return availBalResponse;
    }

    public void setAvailBalResponse(AvailBalResponse availBalResponse) {
        this.availBalResponse = availBalResponse;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }
}