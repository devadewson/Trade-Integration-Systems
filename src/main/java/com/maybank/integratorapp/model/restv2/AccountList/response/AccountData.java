package com.maybank.integratorapp.model.restv2.AccountList.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AccountData {
    @JsonProperty("CIFNo")
    private String cifNo;

    @JsonProperty("AccountDataRecord")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private List<AccountDataRecord> accountDataRecord;

    public String getCifNo() {
        return cifNo;
    }

    public void setCifNo(String cifNo) {
        this.cifNo = cifNo;
    }

    public List<AccountDataRecord> getAccountDataRecord() {
        return accountDataRecord;
    }

    public void setAccountDataRecord(List<AccountDataRecord> accountDataRecord) {
        this.accountDataRecord = accountDataRecord;
    }
}
