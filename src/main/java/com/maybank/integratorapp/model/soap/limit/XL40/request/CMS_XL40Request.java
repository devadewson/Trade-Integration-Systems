package com.maybank.integratorapp.model.soap.limit.XL40.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CMS_XL40Request {
    @JacksonXmlProperty(localName = "loanNumber")
    private String loanNumber;
    @JacksonXmlProperty(localName = "screenid")
    private String screenid;

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public String getScreenid() {
        return screenid;
    }

    public void setScreenid(String screenid) {
        this.screenid = screenid;
    }
}
