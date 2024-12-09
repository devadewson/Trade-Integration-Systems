package com.maybank.integratorapp.model.soap.XLBT.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class loanAccounts {
    @JacksonXmlProperty(localName = "commitmentbalance")
    private String commitmentBalance;
    @JacksonXmlProperty(localName = "commitmentbalancesign")
    private String commitmentBalancesign;
    @JacksonXmlProperty(localName = "description")
    private String description;
    @JacksonXmlProperty(localName = "key")
    private String key;
    @JacksonXmlProperty(localName = "loancurrencycode")
    private String loanCurrencyode;
    @JacksonXmlProperty(localName = "maturitydate")
    private String maturityDate;
    @JacksonXmlProperty(localName = "notedate")
    private String noteDate;
    @JacksonXmlProperty(localName = "notetype")
    private String noteType;
    @JacksonXmlProperty(localName = "principalbalance")
    private String principalBalance;
    @JacksonXmlProperty(localName = "principalbalancesign")
    private String principalbalancesign;
    @JacksonXmlProperty(localName = "status")
    private String status;

    public String getCommitmentBalance() {
        return commitmentBalance;
    }

    public void setCommitmentBalance(String commitmentBalance) {
        this.commitmentBalance = commitmentBalance;
    }

    public String getCommitmentBalancesign() {
        return commitmentBalancesign;
    }

    public void setCommitmentBalancesign(String commitmentBalancesign) {
        this.commitmentBalancesign = commitmentBalancesign;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLoanCurrencyode() {
        return loanCurrencyode;
    }

    public void setLoanCurrencyode(String loanCurrencyode) {
        this.loanCurrencyode = loanCurrencyode;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public String getPrincipalBalance() {
        return principalBalance;
    }

    public void setPrincipalBalance(String principalBalance) {
        this.principalBalance = principalBalance;
    }

    public String getPrincipalbalancesign() {
        return principalbalancesign;
    }

    public void setPrincipalbalancesign(String principalbalancesign) {
        this.principalbalancesign = principalbalancesign;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
