package com.maybank.integratorapp.model.mq.customerdetail.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class AddressDetails {
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "AddressDetail")

    private AddressDetail addressDetail;

    public AddressDetail getAddressDetail() { return addressDetail; }
    public void setAddressDetail(AddressDetail value) { this.addressDetail = value; }
}