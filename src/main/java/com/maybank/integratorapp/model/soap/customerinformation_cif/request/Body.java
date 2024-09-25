package com.maybank.integratorapp.model.soap.customerinformation_cif.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Body {
    public Body(){
        this.customerInformation = new CustomerInformation();
    }
    @JacksonXmlProperty(localName = "CustomerInformation",namespace = "http://www.bankbii.com/AccountServices/")
    private CustomerInformation customerInformation;

    public CustomerInformation getCustomerInformation() {
        return customerInformation;
    }

    public void setCustomerInformation(CustomerInformation customerInformation) {
        this.customerInformation = customerInformation;
    }
}
