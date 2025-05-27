package com.maybank.integratorapp.model.rest.CustomerSearchh.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerInformation {
    @JsonProperty("ChannelHeader")
    private ChannelHeader channelHeader;
    @JsonProperty("CustomerInformationRequest")
    private CustomerInformationRequest customerInformationRequest;

    public CustomerInformation () {
        this.channelHeader = new ChannelHeader();
        this.customerInformationRequest = new CustomerInformationRequest();
    }


    public ChannelHeader getChannelHeader() {
        return channelHeader;
    }

    public void setChannelHeader(ChannelHeader channelHeader) {
        this.channelHeader = channelHeader;
    }

    public CustomerInformationRequest getCustomerInformationRequest() {
        return customerInformationRequest;
    }

    public void setCustomerInformationRequest(CustomerInformationRequest customerInformationRequest) {
        this.customerInformationRequest = customerInformationRequest;
    }
}
