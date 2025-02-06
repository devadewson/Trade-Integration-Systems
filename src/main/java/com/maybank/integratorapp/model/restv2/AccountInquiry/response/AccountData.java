package com.maybank.integratorapp.model.restv2.AccountInquiry.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountData {
    @JsonProperty("SADataRecord")
    private SADataRecord sADataRecord;

    public SADataRecord getsADataRecord() {
        return sADataRecord;
    }

    public void setsADataRecord(SADataRecord sADataRecord) {
        this.sADataRecord = sADataRecord;
    }
}
