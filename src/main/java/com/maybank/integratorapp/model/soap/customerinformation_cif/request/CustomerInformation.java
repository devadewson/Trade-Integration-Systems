package com.maybank.integratorapp.model.soap.customerinformation_cif.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CustomerInformation {
    public CustomerInformation(){
        this.customerInformationRequest = new CustomerInformationRequest();
        this.channelHeader = new com.maybank.integratorapp.model.soap.customerinformation_cif.request.ChannelHeader();
    }
    @JacksonXmlProperty(localName = "ChannelHeader")
    private com.maybank.integratorapp.model.soap.customerinformation_cif.request.ChannelHeader channelHeader;

    @JacksonXmlProperty(localName = "CustomerInformationRequest")
    private CustomerInformationRequest customerInformationRequest;

    public com.maybank.integratorapp.model.soap.customerinformation_cif.request.ChannelHeader getChannelHeader() {
        return channelHeader;
    }

    public void setChannelHeader(ChannelHeader channelHeader) {
        this.channelHeader = channelHeader;
    }

    public CustomerInformationRequest getCustomerInformationRequest() {
        return customerInformationRequest;
    }

    public void setAccountInquiryRequest(CustomerInformationRequest customerInformationRequest) {
        this.customerInformationRequest = customerInformationRequest;
    }
}

