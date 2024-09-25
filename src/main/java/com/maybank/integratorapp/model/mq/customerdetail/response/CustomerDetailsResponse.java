package com.maybank.integratorapp.model.mq.customerdetail.response;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CustomerDetailsResponse {
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "FullName")
    private String fullName;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "CustomerNumber")
    
    private String customerNumber;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "ShortName")

    private String shortName;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "CustomerType")
    
    private String customerType;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Blocked")

    private String blocked;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Closed")

    private String closed;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Deceased")

    private String deceased;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Inactive")

    private String inactive;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "AccountOfficer")

    private String accountOfficer;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Reference")

    private String reference;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Language")

    private String language;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "ParentCountry")

    private String parentCountry;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "RiskCountry")

    private String riskCountry;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "ResidenceCountry")

    private String residenceCountry;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "DefaultBranch")

    private String defaultBranch;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "MailToBranch")

    private String mailToBranch;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "AnalysisCode")

    private String analysisCode;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "BankCode1")

    private String bankCode1;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "BankCode2")

    private String bankCode2;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "BankCode3")

    private String bankCode3;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "BankCode4")

    private String bankCode4;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Group")

    private String group;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "GroupDescription")

    private String groupDescription;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "DateMaintained")

    private String dateMaintained;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "MidasFacilityAllow")

    private String midasFacilityAllow;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "ClearingId")

    private String clearingID;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "Location")

    private String location;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "AddressDetails")

    private AddressDetails addressDetails;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "SpecialInstructionDetails")

    private SpecialInstructionDetails specialInstructionDetails;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "OtherDetails")

    private OtherDetails otherDetails;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "SwiftDetails")

    private SwiftDetails swiftDetails;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "CustomerExtraData")

    private String customerExtraData;
    @JacksonXmlProperty(namespace = "urn:messages.service.ti.apps.tiplus2.misys.com", localName = "TICustomerExtraData")

    private String tiCustomerExtraData;

    public String getFullName() { return fullName; }
    public void setFullName(String value) { this.fullName = value; }

    public String getCustomerNumber() { return customerNumber; }
    public void setCustomerNumber(String value) { this.customerNumber = value; }

    public String getShortName() { return shortName; }
    public void setShortName(String value) { this.shortName = value; }

    public String getCustomerType() { return customerType; }
    public void setCustomerType(String value) { this.customerType = value; }

    public String getBlocked() { return blocked; }
    public void setBlocked(String value) { this.blocked = value; }

    public String getClosed() { return closed; }
    public void setClosed(String value) { this.closed = value; }

    public String getDeceased() { return deceased; }
    public void setDeceased(String value) { this.deceased = value; }

    public String getInactive() { return inactive; }
    public void setInactive(String value) { this.inactive = value; }

    public String getAccountOfficer() { return accountOfficer; }
    public void setAccountOfficer(String value) { this.accountOfficer = value; }

    public String getReference() { return reference; }
    public void setReference(String value) { this.reference = value; }

    public String getLanguage() { return language; }
    public void setLanguage(String value) { this.language = value; }

    public String getParentCountry() { return parentCountry; }
    public void setParentCountry(String value) { this.parentCountry = value; }

    public String getRiskCountry() { return riskCountry; }
    public void setRiskCountry(String value) { this.riskCountry = value; }

    public String getResidenceCountry() { return residenceCountry; }
    public void setResidenceCountry(String value) { this.residenceCountry = value; }

    public String getDefaultBranch() { return defaultBranch; }
    public void setDefaultBranch(String value) { this.defaultBranch = value; }

    public String getMailToBranch() { return mailToBranch; }
    public void setMailToBranch(String value) { this.mailToBranch = value; }

    public String getAnalysisCode() { return analysisCode; }
    public void setAnalysisCode(String value) { this.analysisCode = value; }

    public String getBankCode1() { return bankCode1; }
    public void setBankCode1(String value) { this.bankCode1 = value; }

    public String getBankCode2() { return bankCode2; }
    public void setBankCode2(String value) { this.bankCode2 = value; }

    public String getBankCode3() { return bankCode3; }
    public void setBankCode3(String value) { this.bankCode3 = value; }

    public String getBankCode4() { return bankCode4; }
    public void setBankCode4(String value) { this.bankCode4 = value; }

    public String getGroup() { return group; }
    public void setGroup(String value) { this.group = value; }

    public String getGroupDescription() { return groupDescription; }
    public void setGroupDescription(String value) { this.groupDescription = value; }

    public String getDateMaintained() { return dateMaintained; }
    public void setDateMaintained(String value) { this.dateMaintained = value; }

    public String getMidasFacilityAllow() { return midasFacilityAllow; }
    public void setMidasFacilityAllow(String value) { this.midasFacilityAllow = value; }

    public String getClearingID() { return clearingID; }
    public void setClearingID(String value) { this.clearingID = value; }

    public String getLocation() { return location; }
    public void setLocation(String value) { this.location = value; }

    public AddressDetails getAddressDetails() { return addressDetails; }
    public void setAddressDetails(AddressDetails value) { this.addressDetails = value; }

    public SpecialInstructionDetails getSpecialInstructionDetails() { return specialInstructionDetails; }
    public void setSpecialInstructionDetails(SpecialInstructionDetails value) { this.specialInstructionDetails = value; }

    public OtherDetails getOtherDetails() { return otherDetails; }
    public void setOtherDetails(OtherDetails value) { this.otherDetails = value; }

    public SwiftDetails getSwiftDetails() { return swiftDetails; }
    public void setSwiftDetails(SwiftDetails value) { this.swiftDetails = value; }

    public String getCustomerExtraData() { return customerExtraData; }
    public void setCustomerExtraData(String value) { this.customerExtraData = value; }

    public String getTICustomerExtraData() { return tiCustomerExtraData; }
    public void setTICustomerExtraData(String value) { this.tiCustomerExtraData = value; }

}
