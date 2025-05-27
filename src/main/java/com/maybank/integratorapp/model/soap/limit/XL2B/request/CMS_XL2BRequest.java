package com.maybank.integratorapp.model.soap.limit.XL2B.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CMS_XL2BRequest {
    @JacksonXmlProperty(localName = "avlPeriode")
    private String avlPeriode;

    @JacksonXmlProperty(localName = "maturity")
    private String maturity;

    @JacksonXmlProperty(localName = "noteNo")
    private String noteNo;

    @JacksonXmlProperty(localName = "officerCode")
    private String officerCode;

    @JacksonXmlProperty(localName = "promoCode")
    private String promoCode;

    @JacksonXmlProperty(localName = "rate")
    private String rate;

    @JacksonXmlProperty(localName = "usercode_3")
    private String usercode_3;

    public String getAvlPeriode() {
        return avlPeriode;
    }

    public void setAvlPeriode(String avlPeriode) {
        this.avlPeriode = avlPeriode;
    }

    public String getMaturity() {
        return maturity;
    }

    public void setMaturity(String maturity) {
        this.maturity = maturity;
    }

    public String getNoteNo() {
        return noteNo;
    }

    public void setNoteNo(String noteNo) {
        this.noteNo = noteNo;
    }

    public String getOfficerCode() {
        return officerCode;
    }

    public void setOfficerCode(String officerCode) {
        this.officerCode = officerCode;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getUsercode_3() {
        return usercode_3;
    }

    public void setUsercode_3(String usercode_3) {
        this.usercode_3 = usercode_3;
    }
}
