package com.maybank.integratorapp.model.mq.customerdetail.response;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class AddressDetail {
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "AddressType")
    private String addressType;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "AddressId")

    private String addressID;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Salutation")
    private String salutation;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "NameAndAddress")
    private String nameAndAddress;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "ZipCode")
    private String zipCode;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Language")
    private String language;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Locale")
    private String locale;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Phone")
    private String phone;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Fax")
    private String fax;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Telex")
    private String telex;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "TelexAnswerBack")
    private String telexAnswerBack;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Email")
    private String email;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "SwiftBIC")
    private String swiftBIC;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "TransferMethod")
    private String transferMethod;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "AddresseeCustomer")
    private String addresseeCustomer;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "NumberOfCopies")
    private String numberOfCopies;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "NumberOfOriginals")
    private String numberOfOriginals;

    public String getAddressType() { return addressType; }
    public void setAddressType(String value) { this.addressType = value; }

    public String getAddressID() { return addressID; }
    public void setAddressID(String value) { this.addressID = value; }

    public String getSalutation() { return salutation; }
    public void setSalutation(String value) { this.salutation = value; }

    public String getNameAndAddress() { return nameAndAddress; }
    public void setNameAndAddress(String value) { this.nameAndAddress = value; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String value) { this.zipCode = value; }

    public String getLanguage() { return language; }
    public void setLanguage(String value) { this.language = value; }

    public String getLocale() { return locale; }
    public void setLocale(String value) { this.locale = value; }

    public String getPhone() { return phone; }
    public void setPhone(String value) { this.phone = value; }

    public String getFax() { return fax; }
    public void setFax(String value) { this.fax = value; }

    public String getTelex() { return telex; }
    public void setTelex(String value) { this.telex = value; }

    public String getTelexAnswerBack() { return telexAnswerBack; }
    public void setTelexAnswerBack(String value) { this.telexAnswerBack = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public String getSwiftBIC() { return swiftBIC; }
    public void setSwiftBIC(String value) { this.swiftBIC = value; }

    public String getTransferMethod() { return transferMethod; }
    public void setTransferMethod(String value) { this.transferMethod = value; }

    public String getAddresseeCustomer() { return addresseeCustomer; }
    public void setAddresseeCustomer(String value) { this.addresseeCustomer = value; }

    public String getNumberOfCopies() { return numberOfCopies; }
    public void setNumberOfCopies(String value) { this.numberOfCopies = value; }

    public String getNumberOfOriginals() { return numberOfOriginals; }
    public void setNumberOfOriginals(String value) { this.numberOfOriginals = value; }

}

