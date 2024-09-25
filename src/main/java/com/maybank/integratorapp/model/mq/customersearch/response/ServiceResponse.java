package com.maybank.integratorapp.model.mq.customersearch.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.maybank.integratorapp.model.mq.accountinquiry.response.AvailBalResponse;

@JacksonXmlRootElement(localName = "ServiceResponse")
public class ServiceResponse {

    @JacksonXmlProperty(localName = "ResponseHeader")
    private ResponseHeader responseHeader;

    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com",localName = "CustomerSearchResponse")
    private CustomerSearchResponse customerSearchResponse;

    // Getters and setters
    public ServiceResponse(){
        ResponseHeader header = new ResponseHeader();
        header.setDetails(new Details());
        this.responseHeader = header;
        this.customerSearchResponse = new CustomerSearchResponse();

    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public CustomerSearchResponse getCustomerSearchResponse() {
        return customerSearchResponse;
    }

    public void setCustomerSearchResponse(CustomerSearchResponse customerSearchResponse) {
        this.customerSearchResponse = customerSearchResponse;
    }
}