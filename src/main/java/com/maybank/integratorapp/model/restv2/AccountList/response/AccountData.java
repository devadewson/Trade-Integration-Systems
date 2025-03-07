package com.maybank.integratorapp.model.restv2.AccountList.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AccountData {
    @JsonProperty("CIFNo")
    private String cifNo;

    @JsonProperty("AccountDataRecord")
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
