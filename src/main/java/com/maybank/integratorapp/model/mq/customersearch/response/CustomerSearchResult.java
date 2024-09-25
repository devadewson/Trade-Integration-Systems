package com.maybank.integratorapp.model.mq.customersearch.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CustomerSearchResult {
    @JacksonXmlProperty(localName = "CustomerMnemonic", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String customerMnemonic;
    @JacksonXmlProperty(localName = "FullName", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String fullName;
    @JacksonXmlProperty(localName = "CustomerNumber", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String customerNumber;
    @JacksonXmlProperty(localName = "CountryOfResidence", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String countryOfResidence;
    @JacksonXmlProperty(localName = "AccountOfficer", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String accountOfficer;
    @JacksonXmlProperty(localName = "Blocked", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String blocked;
    @JacksonXmlProperty(localName = "Group", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String group;
    @JacksonXmlProperty(localName = "Location", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String location;


    public String getCustomerMnemonic() {
        return customerMnemonic;
    }

    public void setCustomerMnemonic(String customerMnemonic) {
        this.customerMnemonic = customerMnemonic;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public String getAccountOfficer() {
        return accountOfficer;
    }

    public void setAccountOfficer(String accountOfficer) {
        this.accountOfficer = accountOfficer;
    }

    public String getBlocked() {
        return blocked;
    }

    public void setBlocked(String blocked) {
        this.blocked = blocked;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
