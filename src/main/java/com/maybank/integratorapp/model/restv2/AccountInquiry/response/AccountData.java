package com.maybank.integratorapp.model.restv2.AccountInquiry.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountData {
    @JsonProperty("CADataRecord")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private CADataRecord cADataRecord;

    public CADataRecord getcADataRecord() {
        return cADataRecord;
    }

    public void setcADataRecord(CADataRecord cADataRecord) {
        this.cADataRecord = cADataRecord;
    }
}
