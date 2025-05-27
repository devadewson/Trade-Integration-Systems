package com.maybank.integratorapp.model.rest.CustomerDetail.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerInformationWraper {

    public CustomerInformationWraper() {
        this.customerInformation = new CustomerInformation();
    }

    @JsonProperty("CustomerInformation")
    private  CustomerInformation customerInformation;


    public CustomerInformation getCustomerInformation() {
        return customerInformation;
    }

    public void setCustomerInformation(CustomerInformation customerInformation) {
        this.customerInformation = customerInformation;
    }
}
