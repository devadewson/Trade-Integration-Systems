package com.maybank.integratorapp.model.soap.limit.XLBT.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class CMS_XLBTResponse {

    @JacksonXmlProperty(localName = "responseDetail")
    private ResponseDetail responseDetail;
    @JacksonXmlProperty(localName = "responsecode")
    private String responsecode;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "loanAccounts")
    private List<LoanAccounts> loanAccounts;

    public ResponseDetail getResponseDetail() {
        return responseDetail;
    }

    public void setResponseDetail(ResponseDetail responseDetail) {
        this.responseDetail = responseDetail;
    }

    public String getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }

    public List<LoanAccounts> getLoanAccounts() {
        return loanAccounts;
    }

    public void setLoanAccounts(List<LoanAccounts> loanAccounts) {
        this.loanAccounts = loanAccounts;
    }
}
