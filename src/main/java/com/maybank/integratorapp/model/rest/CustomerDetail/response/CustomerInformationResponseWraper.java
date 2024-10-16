package com.maybank.integratorapp.model.rest.CustomerDetail.response;

import com.fasterxml.jackson.annotation.JsonProperty;


public class CustomerInformationResponseWraper {
    public CustomerInformationResponseWraper() {
        this.customerInformationResponse = new CustomerInformationResponse();
    }

    @JsonProperty("CustomerInformationResponse")
    private CustomerInformationResponse customerInformationResponse;

    public CustomerInformationResponse getCustomerInformationResponse() {
        return customerInformationResponse;
    }

    public void setCustomerInformationResponse(CustomerInformationResponse customerInformationResponse) {
        this.customerInformationResponse = customerInformationResponse;
    }
}
