package com.maybank.integratorapp.model.rest.AccountList.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "WealthAccountData")
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
