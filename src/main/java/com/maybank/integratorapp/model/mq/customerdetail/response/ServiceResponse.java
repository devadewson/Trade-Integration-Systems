package com.maybank.integratorapp.model.mq.customerdetail.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "ServiceResponse")
public class ServiceResponse {

    @JacksonXmlProperty(localName = "ResponseHeader")
    private ResponseHeader responseHeader;

    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "CustomerDetailsResponse")
    private CustomerDetailsResponse customerDetailsResponse;

    // Getters and setters
    public ServiceResponse(){
        ResponseHeader header = new ResponseHeader();
        header.setDetails(new Details());
        this.responseHeader = header;
        this.customerDetailsResponse = new CustomerDetailsResponse();

    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public CustomerDetailsResponse getAvailBalResponse() {
        return customerDetailsResponse;
    }

    public void setAvailBalResponse(CustomerDetailsResponse customerDetailsResponse) {
        this.customerDetailsResponse = customerDetailsResponse;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }
}