package com.maybank.integratorapp.model.soap.limit.XL31.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CMS_XL31Request {
    @JacksonXmlProperty(localName = "amount")
    private String amount;
    @JacksonXmlProperty(localName = "batch")
    private String batch;
    @JacksonXmlProperty(localName = "bd")
    private String bd;
    @JacksonXmlProperty(localName = "currency")
    private String currency;
    @JacksonXmlProperty(localName = "departement")
    private String departement;
    @JacksonXmlProperty(localName = "description")
    private String description;
    @JacksonXmlProperty(localName = "notenumber")
    private String notenumber;
    @JacksonXmlProperty(localName = "qual")
    private String qual;
    @JacksonXmlProperty(localName = "tran")
    private String tran;
    @JacksonXmlProperty(localName = "transactiondate")
    private String transactiondate;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getBd() {
        return bd;
    }

    public void setBd(String bd) {
        this.bd = bd;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotenumber() {
        return notenumber;
    }

    public void setNotenumber(String notenumber) {
        this.notenumber = notenumber;
    }

    public String getQual() {
        return qual;
    }

    public void setQual(String qual) {
        this.qual = qual;
    }

    public String getTran() {
        return tran;
    }

    public void setTran(String tran) {
        this.tran = tran;
    }

    public String getTransactiondate() {
        return transactiondate;
    }

    public void setTransactiondate(String transactiondate) {
        this.transactiondate = transactiondate;
    }
}
