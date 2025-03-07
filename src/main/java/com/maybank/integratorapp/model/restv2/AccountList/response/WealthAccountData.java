package com.maybank.integratorapp.model.restv2.AccountList.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WealthAccountData {
    @JsonProperty("WealthAccountDataRecord")
    private List<WealthAccountDataRecord> wealthAccountDataRecord;

    public List<WealthAccountDataRecord> getWealthAccountDataRecord() {
        return wealthAccountDataRecord;
    }

    public void setWealthAccountDataRecord(List<WealthAccountDataRecord> wealthAccountDataRecord) {
        this.wealthAccountDataRecord = wealthAccountDataRecord;
    }
}
