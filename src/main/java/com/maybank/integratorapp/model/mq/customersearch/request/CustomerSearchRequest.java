package com.maybank.integratorapp.model.mq.customersearch.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CustomerSearchRequest {
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
    @JacksonXmlProperty(localName = "Group", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String group;
    @JacksonXmlProperty(localName = "Location", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String location;
    @JacksonXmlProperty(localName = "SwiftAddress", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String swiftAddress;
    @JacksonXmlProperty(localName = "IncludeCustomers", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String includeCustomers;
    @JacksonXmlProperty(localName = "IncludeCorrespondents", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String includeCorrespondents;
    @JacksonXmlProperty(localName = "IncludeBanks", namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private String includeBanks;

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

    public String getSwiftAddress() {
        return swiftAddress;
    }

    public void setSwiftAddress(String swiftAddress) {
        this.swiftAddress = swiftAddress;
    }

    public String getIncludeCustomers() {
        return includeCustomers;
    }

    public void setIncludeCustomers(String includeCustomers) {
        this.includeCustomers = includeCustomers;
    }

    public String getIncludeCorrespondents() {
        return includeCorrespondents;
    }

    public void setIncludeCorrespondents(String includeCorrespondents) {
        this.includeCorrespondents = includeCorrespondents;
    }

    public String getIncludeBanks() {
        return includeBanks;
    }

    public void setIncludeBanks(String includeBanks) {
        this.includeBanks = includeBanks;
    }
}
