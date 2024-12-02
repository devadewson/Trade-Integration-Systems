package com.maybank.integratorapp.model.mq.customerdetail.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "ServiceResponse",namespace = "urn:control.services.tiplus2.misys.com")
public class ServiceResponse {

    @JacksonXmlProperty(localName = "ResponseHeader",namespace = "urn:control.services.tiplus2.misys.com")
    private ResponseHeader responseHeader;

    @JacksonXmlProperty(localName = "CustomerDetailsResponse", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
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

    public CustomerDetailsResponse getCustomerDetailsResponse() {
        return customerDetailsResponse;
    }

    public void setCustomerDetailsResponse(CustomerDetailsResponse customerDetailsResponse) {
        this.customerDetailsResponse = customerDetailsResponse;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }
}