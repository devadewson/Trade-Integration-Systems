package com.maybank.integratorapp.model.soap.XLBT.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class CMS_XLBTResponse {

    @JacksonXmlProperty(localName = "responseDetail",namespace = "http://www.bankbii.com/AccountServices/")
    private responseDetail responseDetail  ;
    @JacksonXmlProperty(localName = "responsecode")
    private String responseCode;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "loanAccounts",namespace = "urn:messages.service.ti.apps.tiplus2.misys.com")
    private List<loanAccounts> loanAccounts ;

    public com.maybank.integratorapp.model.soap.XLBT.response.responseDetail getResponseDetail() {
        return responseDetail;
    }

    public void setResponseDetail(com.maybank.integratorapp.model.soap.XLBT.response.responseDetail responseDetail) {
        this.responseDetail = responseDetail;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public List<com.maybank.integratorapp.model.soap.XLBT.response.loanAccounts> getLoanAccounts() {
        return loanAccounts;
    }

    public void setLoanAccounts(List<com.maybank.integratorapp.model.soap.XLBT.response.loanAccounts> loanAccounts) {
        this.loanAccounts = loanAccounts;
    }
}
