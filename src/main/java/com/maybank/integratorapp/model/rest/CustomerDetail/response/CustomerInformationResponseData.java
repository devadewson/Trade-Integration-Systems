package com.maybank.integratorapp.model.rest.CustomerDetail.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "CustomerInformationResponseData")
public class CustomerInformationResponseData {
    @JsonProperty("GCIFNo")
    private String GCIFNo;
    @JsonProperty("CustomerType")
    private String CustomerType;
    @JsonProperty("FullName")
    private String FullName;
    @JsonProperty("CustomerClass")
    private String CustomerClass;
    @JsonProperty("CIFBranch")
    private String CIFBranch;
    @JsonProperty("FATCA")
    private String FATCA;
    @JsonProperty("Nationality")
    private String Nationality;
    @JsonProperty("ResidenceStatus")
    private String ResidenceStatus;
    @JsonProperty("BirthDate")
    private String BirthDate;
    @JsonProperty( "NPWP")
    private String NPWP;
    @JsonProperty("MobileNo")
    private String MobileNo;
    @JsonProperty("Email")
    private String Email;
    @JsonProperty("IDType")
    private String IDType;
    @JsonProperty("IDNumber")
    private String IDNumber;
    @JsonProperty("IDExpiredDate")
    private String IDExpiredDate;
    @JsonProperty("Title")
    private String Title;
    @JsonProperty("LineOfBusiness")
    private String LineOfBusiness;
    @JsonProperty("Gender")
    private String Gender;
    @JsonProperty("BirthPlace")
    private String BirthPlace;
    @JsonProperty("MotherName")
    private String MotherName;
    @JsonProperty("AddressLine1")
    private String AddressLine1;
    @JsonProperty("AddressLine2")
    private String AddressLine2;
    @JsonProperty("AddressLine3")
    private String AddressLine3;
    @JsonProperty("AddressLine4")
    private String AddressLine4;
    @JsonProperty("AddressLine5")
    private String AddressLine5;
    @JsonProperty("AddressLine6")
    private String AddressLine6;
    @JsonProperty("AddressLine7")
    private String AddressLine7;
    @JsonProperty("AddressLine8")
    private String AddressLine8;
    @JsonProperty("AddressLine9")
    private String AddressLine9;
    @JsonProperty("AddressLine10")
    private String AddressLine10;
    @JsonProperty("MailAddressLine1")
    private String MailAddressLine1;
    @JsonProperty("MailAddressLine2")
    private String MailAddressLine2;
    @JsonProperty("MailAddressLine3")
    private String MailAddressLine3;
    @JsonProperty("MailAddressLine4")
    private String MailAddressLine4;
    @JsonProperty("MailAddressLine5")
    private String MailAddressLine5;
    @JsonProperty("MailAddressLine6")
    private String MailAddressLine6;
    @JsonProperty("MailAddressLine7")
    private String MailAddressLine7;
    @JsonProperty("MailAddressLine8")
    private String MailAddressLine8;
    @JsonProperty("MailAddressLine9")
    private String MailAddressLine9;
    @JsonProperty("MailAddressLine10")
    private String MailAddressLine10;
    @JsonProperty("ShareHolderName1")
    private String ShareHolderName1;
    @JsonProperty("ShareHolderPct1")
    private String ShareHolderPct1;
    @JsonProperty("ShareHolderName2")
    private String ShareHolderName2;
    @JsonProperty("ShareHolderPct2")
    private String ShareHolderPct2;
    @JsonProperty("ShareHolderName3")
    private String ShareHolderName3;
    @JsonProperty("ShareHolderPct3")
    private String ShareHolderPct3;
    @JsonProperty("AuthorizedPersonName1")
    private String AuthorizedPersonName1;
    @JsonProperty("AuthorizedPersonIDType1")
    private String AuthorizedPersonIDType1;
    @JsonProperty("AuthorizedPersonIDNo1")
    private String AuthorizedPersonIDNo1;

    public String getGCIFNo() {
        return GCIFNo;
    }

    public void setGCIFNo(String GCIFNo) {
        this.GCIFNo = GCIFNo;
    }

    public String getCustomerType() {
        return CustomerType;
    }

    public void setCustomerType(String customerType) {
        CustomerType = customerType;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getCustomerClass() {
        return CustomerClass;
    }

    public void setCustomerClass(String customerClass) {
        CustomerClass = customerClass;
    }

    public String getCIFBranch() {
        return CIFBranch;
    }

    public void setCIFBranch(String CIFBranch) {
        this.CIFBranch = CIFBranch;
    }

    public String getFATCA() {
        return FATCA;
    }

    public void setFATCA(String FATCA) {
        this.FATCA = FATCA;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getResidenceStatus() {
        return ResidenceStatus;
    }

    public void setResidenceStatus(String residenceStatus) {
        ResidenceStatus = residenceStatus;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getNPWP() {
        return NPWP;
    }

    public void setNPWP(String NPWP) {
        this.NPWP = NPWP;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getIDType() {
        return IDType;
    }

    public void setIDType(String IDType) {
        this.IDType = IDType;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public String getIDExpiredDate() {
        return IDExpiredDate;
    }

    public void setIDExpiredDate(String IDExpiredDate) {
        this.IDExpiredDate = IDExpiredDate;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLineOfBusiness() {
        return LineOfBusiness;
    }

    public void setLineOfBusiness(String lineOfBusiness) {
        LineOfBusiness = lineOfBusiness;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBirthPlace() {
        return BirthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        BirthPlace = birthPlace;
    }

    public String getMotherName() {
        return MotherName;
    }

    public void setMotherName(String motherName) {
        MotherName = motherName;
    }

    public String getAddressLine1() {
        return AddressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        AddressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return AddressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        AddressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return AddressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        AddressLine3 = addressLine3;
    }

    public String getAddressLine4() {
        return AddressLine4;
    }

    public void setAddressLine4(String addressLine4) {
        AddressLine4 = addressLine4;
    }

    public String getAddressLine5() {
        return AddressLine5;
    }

    public void setAddressLine5(String addressLine5) {
        AddressLine5 = addressLine5;
    }

    public String getAddressLine6() {
        return AddressLine6;
    }

    public void setAddressLine6(String addressLine6) {
        AddressLine6 = addressLine6;
    }

    public String getAddressLine7() {
        return AddressLine7;
    }

    public void setAddressLine7(String addressLine7) {
        AddressLine7 = addressLine7;
    }

    public String getAddressLine8() {
        return AddressLine8;
    }

    public void setAddressLine8(String addressLine8) {
        AddressLine8 = addressLine8;
    }

    public String getAddressLine9() {
        return AddressLine9;
    }

    public void setAddressLine9(String addressLine9) {
        AddressLine9 = addressLine9;
    }

    public String getAddressLine10() {
        return AddressLine10;
    }

    public void setAddressLine10(String addressLine10) {
        AddressLine10 = addressLine10;
    }

    public String getMailAddressLine1() {
        return MailAddressLine1;
    }

    public void setMailAddressLine1(String mailAddressLine1) {
        MailAddressLine1 = mailAddressLine1;
    }

    public String getMailAddressLine2() {
        return MailAddressLine2;
    }

    public void setMailAddressLine2(String mailAddressLine2) {
        MailAddressLine2 = mailAddressLine2;
    }

    public String getMailAddressLine3() {
        return MailAddressLine3;
    }

    public void setMailAddressLine3(String mailAddressLine3) {
        MailAddressLine3 = mailAddressLine3;
    }

    public String getMailAddressLine4() {
        return MailAddressLine4;
    }

    public void setMailAddressLine4(String mailAddressLine4) {
        MailAddressLine4 = mailAddressLine4;
    }

    public String getMailAddressLine5() {
        return MailAddressLine5;
    }

    public void setMailAddressLine5(String mailAddressLine5) {
        MailAddressLine5 = mailAddressLine5;
    }

    public String getMailAddressLine6() {
        return MailAddressLine6;
    }

    public void setMailAddressLine6(String mailAddressLine6) {
        MailAddressLine6 = mailAddressLine6;
    }

    public String getMailAddressLine7() {
        return MailAddressLine7;
    }

    public void setMailAddressLine7(String mailAddressLine7) {
        MailAddressLine7 = mailAddressLine7;
    }

    public String getMailAddressLine8() {
        return MailAddressLine8;
    }

    public void setMailAddressLine8(String mailAddressLine8) {
        MailAddressLine8 = mailAddressLine8;
    }

    public String getMailAddressLine9() {
        return MailAddressLine9;
    }

    public void setMailAddressLine9(String mailAddressLine9) {
        MailAddressLine9 = mailAddressLine9;
    }

    public String getMailAddressLine10() {
        return MailAddressLine10;
    }

    public void setMailAddressLine10(String mailAddressLine10) {
        MailAddressLine10 = mailAddressLine10;
    }

    public String getShareHolderName1() {
        return ShareHolderName1;
    }

    public void setShareHolderName1(String shareHolderName1) {
        ShareHolderName1 = shareHolderName1;
    }

    public String getShareHolderPct1() {
        return ShareHolderPct1;
    }

    public String getShareHolderName2() {
        return ShareHolderName2;
    }

    public void setShareHolderName2(String shareHolderName2) {
        ShareHolderName2 = shareHolderName2;
    }

    public String getShareHolderPct2() {
        return ShareHolderPct2;
    }

    public void setShareHolderPct2(String shareHolderPct2) {
        ShareHolderPct2 = shareHolderPct2;
    }

    public String getShareHolderName3() {
        return ShareHolderName3;
    }

    public void setShareHolderName3(String shareHolderName3) {
        ShareHolderName3 = shareHolderName3;
    }

    public String getShareHolderPct3() {
        return ShareHolderPct3;
    }

    public void setShareHolderPct3(String shareHolderPct3) {
        ShareHolderPct3 = shareHolderPct3;
    }

    public void setShareHolderPct1(String shareHolderPct1) {
        ShareHolderPct1 = shareHolderPct1;
    }

    public String getAuthorizedPersonName1() {
        return AuthorizedPersonName1;
    }

    public void setAuthorizedPersonName1(String authorizedPersonName1) {
        AuthorizedPersonName1 = authorizedPersonName1;
    }

    public String getAuthorizedPersonIDType1() {
        return AuthorizedPersonIDType1;
    }

    public void setAuthorizedPersonIDType1(String authorizedPersonIDType1) {
        AuthorizedPersonIDType1 = authorizedPersonIDType1;
    }

    public String getAuthorizedPersonIDNo1() {
        return AuthorizedPersonIDNo1;
    }

    public void setAuthorizedPersonIDNo1(String authorizedPersonIDNo1) {
        AuthorizedPersonIDNo1 = authorizedPersonIDNo1;
    }
}
