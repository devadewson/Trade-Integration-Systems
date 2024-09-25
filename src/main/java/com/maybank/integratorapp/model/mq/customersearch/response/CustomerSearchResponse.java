package com.maybank.integratorapp.model.mq.customersearch.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CustomerSearchResponse {

    public CustomerSearchResponse(){
        this.customerSearchResults = new CustomerSearchResults();
    }
    @JacksonXmlProperty(localName = "CustomerSearchResults",namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private CustomerSearchResults customerSearchResults;

    public CustomerSearchResults getCustomerSearchResults() {
        return customerSearchResults;
    }

    public void setCustomerSearchResults(CustomerSearchResults customerSearchResults) {
        this.customerSearchResults = customerSearchResults;
    }
}
