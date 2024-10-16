package com.maybank.integratorapp.model.rest.AccountList.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "CreditCardData")
public class CreditCardData {
    @JsonProperty("CustomerNo")
    private String customerNo;
    @JsonProperty("CreditCardDataRecord")
    private List<CreditCardDataRecord> creditCardDataRecord;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public List<CreditCardDataRecord> getCreditCardDataRecord() {
        return creditCardDataRecord;
    }

    public void setCreditCardDataRecord(List<CreditCardDataRecord> creditCardDataRecord) {
        this.creditCardDataRecord = creditCardDataRecord;
    }
}
