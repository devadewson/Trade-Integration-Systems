package com.maybank.integratorapp.model.soap.accountinformation.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class AccountInformationAccountData {
    @JacksonXmlProperty(localName = "CADataRecord")
    AccountInformationCADataRecord AccountInformationCADataRecordObject;


    // Getter Methods

    public AccountInformationCADataRecord getCADataRecord() {
        return AccountInformationCADataRecordObject;
    }

    // Setter Methods

    public void setCADataRecord(AccountInformationCADataRecord AccountInformationCADataRecordObject) {
        this.AccountInformationCADataRecordObject = AccountInformationCADataRecordObject;
    }
}