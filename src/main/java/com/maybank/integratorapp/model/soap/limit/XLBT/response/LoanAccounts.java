package com.maybank.integratorapp.model.soap.limit.XLBT.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class LoanAccounts {
    @JacksonXmlProperty(localName = "commitmentbalance")
    private String commitmentbalance;

    @JacksonXmlProperty(localName = "commitmentbalancesign")
    private String commitmentbalancesign;

    @JacksonXmlProperty(localName = "description")
    private String description;

    @JacksonXmlProperty(localName = "key")
    private String key;

    @JacksonXmlProperty(localName = "loancurrencycode")
    private String loancurrencycode;

    @JacksonXmlProperty(localName = "maturitydate")
    private String maturitydate;

    @JacksonXmlProperty(localName = "notedate")
    private String notedate;

    @JacksonXmlProperty(localName = "notetype")
    private String notetype;

    @JacksonXmlProperty(localName = "principalbalance")
    private String principalbalance;

    @JacksonXmlProperty(localName = "principalbalancesign")
    private String principalbalancesign;

    @JacksonXmlProperty(localName = "status")
    private String status;

    public String getCommitmentbalance() {
        return commitmentbalance;
    }

    public void setCommitmentbalance(String commitmentbalance) {
        this.commitmentbalance = commitmentbalance;
    }

    public String getCommitmentbalancesign() {
        return commitmentbalancesign;
    }

    public void setCommitmentbalancesign(String commitmentbalancesign) {
        this.commitmentbalancesign = commitmentbalancesign;
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

    public String getLoancurrencycode() {
        return loancurrencycode;
    }

    public void setLoancurrencycode(String loancurrencycode) {
        this.loancurrencycode = loancurrencycode;
    }

    public String getMaturitydate() {
        return maturitydate;
    }

    public void setMaturitydate(String maturitydate) {
        this.maturitydate = maturitydate;
    }

    public String getNotedate() {
        return notedate;
    }

    public void setNotedate(String notedate) {
        this.notedate = notedate;
    }

    public String getNotetype() {
        return notetype;
    }

    public void setNotetype(String notetype) {
        this.notetype = notetype;
    }

    public String getPrincipalbalance() {
        return principalbalance;
    }

    public void setPrincipalbalance(String principalbalance) {
        this.principalbalance = principalbalance;
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
