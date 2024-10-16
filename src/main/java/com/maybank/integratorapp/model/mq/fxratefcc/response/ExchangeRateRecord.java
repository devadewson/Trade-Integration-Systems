package com.maybank.integratorapp.model.mq.fxratefcc.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ExchangeRateRecord {
    @JacksonXmlProperty(localName = "brch_code")
    private String brchCode;
    @JacksonXmlProperty(localName = "bank_abbv_name")
    private String bankAbbvName;
    @JacksonXmlProperty(localName = "iso_code")

    private String isoCode;
    @JacksonXmlProperty(localName = "base_iso_code")

    private String baseISOCode;
    @JacksonXmlProperty(localName = "paty_val")

    private String patyVal;
    @JacksonXmlProperty(localName = "buy_tt_rate")

    private String buyTtRate;
    @JacksonXmlProperty(localName = "mid_tt_rate")

    private String midTtRate;
    @JacksonXmlProperty(localName = "sell_tt_rate")

    private String sellTtRate;
    @JacksonXmlProperty(localName = "euro_in_currency")
    private String euroInCurrency;
    @JacksonXmlProperty(localName = "euro_rate")

    private String euroRate;
    @JacksonXmlProperty(localName = "update_date")

    private String updateDate;
    @JacksonXmlProperty(localName = "start_value_date")

    private String startValueDate;
    @JacksonXmlProperty(localName = "end_value_date")

    private String endValueDate;

    public String getBrchCode() {
        return brchCode;
    }

    public void setBrchCode(String brchCode) {
        this.brchCode = brchCode;
    }

    public String getBankAbbvName() {
        return bankAbbvName;
    }

    public void setBankAbbvName(String bankAbbvName) {
        this.bankAbbvName = bankAbbvName;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getBaseISOCode() {
        return baseISOCode;
    }

    public void setBaseISOCode(String baseISOCode) {
        this.baseISOCode = baseISOCode;
    }

    public String getPatyVal() {
        return patyVal;
    }

    public void setPatyVal(String patyVal) {
        this.patyVal = patyVal;
    }

    public String getBuyTtRate() {
        return buyTtRate;
    }

    public void setBuyTtRate(String buyTtRate) {
        this.buyTtRate = buyTtRate;
    }

    public String getMidTtRate() {
        return midTtRate;
    }

    public void setMidTtRate(String midTtRate) {
        this.midTtRate = midTtRate;
    }

    public String getSellTtRate() {
        return sellTtRate;
    }

    public void setSellTtRate(String sellTtRate) {
        this.sellTtRate = sellTtRate;
    }

    public String getEuroInCurrency() {
        return euroInCurrency;
    }

    public void setEuroInCurrency(String euroInCurrency) {
        this.euroInCurrency = euroInCurrency;
    }

    public String getEuroRate() {
        return euroRate;
    }

    public void setEuroRate(String euroRate) {
        this.euroRate = euroRate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getStartValueDate() {
        return startValueDate;
    }

    public void setStartValueDate(String startValueDate) {
        this.startValueDate = startValueDate;
    }

    public String getEndValueDate() {
        return endValueDate;
    }

    public void setEndValueDate(String endValueDate) {
        this.endValueDate = endValueDate;
    }

    @Override
    public String toString() {
        return "ExchangeRateRecord{" +
                "brchCode='" + brchCode + '\'' +
                ", bankAbbvName='" + bankAbbvName + '\'' +
                ", isoCode='" + isoCode + '\'' +
                ", baseISOCode='" + baseISOCode + '\'' +
                ", patyVal='" + patyVal + '\'' +
                ", buyTtRate='" + buyTtRate + '\'' +
                ", midTtRate='" + midTtRate + '\'' +
                ", sellTtRate='" + sellTtRate + '\'' +
                ", euroInCurrency='" + euroInCurrency + '\'' +
                ", euroRate='" + euroRate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", startValueDate='" + startValueDate + '\'' +
                ", endValueDate='" + endValueDate + '\'' +
                '}';
    }
}
