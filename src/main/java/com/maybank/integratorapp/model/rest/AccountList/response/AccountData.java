package com.maybank.integratorapp.model.rest.AccountList.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "AccountData")
public class AccountData {
    @JsonProperty("CIFNo")
    private String CifNo;

    @JsonProperty("AccountDataRecord")
    private List<AccountDataRecord> accountDataRecord;

    public String getCifNo() {
        return CifNo;
    }

    public void setCifNo(String cifNo) {
        CifNo = cifNo;
    }

    public List<AccountDataRecord> getAccountDataRecord() {
        return accountDataRecord;
    }

    public void setAccountDataRecord(List<AccountDataRecord> accountDataRecord) {
        this.accountDataRecord = accountDataRecord;
    }
}
