package com.maybank.integratorapp.model.soap.customerinformation_cif.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CustomerInformationRequest {

    @JacksonXmlProperty(localName = "CIFNo")
    private String CIFNo;

    // Getters and setters

    public void setAccountNo(String accountNo) {
        this.CIFNo = accountNo;
    }

    public String getAccountNo() {
        return CIFNo;
    }

}