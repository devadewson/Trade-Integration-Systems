package com.maybank.integratorapp.model.restv2.AccountList.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BancAssuranceAccountData {
    @JsonProperty("BancAssuranceDataRecord")
    private List<BancAssuranceDataRecord> bancAssuranceDataRecord;

    public List<BancAssuranceDataRecord> getBancAssuranceDataRecord() {
        return bancAssuranceDataRecord;
    }

    public void setBancAssuranceDataRecord(List<BancAssuranceDataRecord> bancAssuranceDataRecord) {
        this.bancAssuranceDataRecord = bancAssuranceDataRecord;
    }
}
