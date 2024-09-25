package com.maybank.integratorapp.model.mq.customersearch.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;

public class CustomerSearchResults {

    public CustomerSearchResults(){
        this.customerSearchResult = new ArrayList<CustomerSearchResult>();
    }
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "CustomerSearchResult",namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private List<CustomerSearchResult> customerSearchResult;

    public List<CustomerSearchResult> getCustomerSearchResult() {
        return customerSearchResult;
    }

    public void setCustomerSearchResult(List<CustomerSearchResult> customerSearchResult) {
        this.customerSearchResult = customerSearchResult;
    }
}
