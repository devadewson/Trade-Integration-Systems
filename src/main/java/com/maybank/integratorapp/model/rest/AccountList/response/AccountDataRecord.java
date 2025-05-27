package com.maybank.integratorapp.model.rest.AccountList.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "AccountData")
public class AccountDataRecord {
    @JsonProperty("AccountNo")
    private String accountNo;

    @JsonProperty("AccountCcy")
    private String accountCcy;

    @JsonProperty("AccountBranchCode")
    private String accountBranchCode;

    @JsonProperty("ApplCode")
    private String applCode;

    @JsonProperty("ProductCode")
    private String productCode;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("RelationCode")
    private String relationCode;

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountCcy() {
        return accountCcy;
    }

    public void setAccountCcy(String accountCcy) {
        this.accountCcy = accountCcy;
    }

    public String getAccountBranchCode() {
        return accountBranchCode;
    }

    public void setAccountBranchCode(String accountBranchCode) {
        this.accountBranchCode = accountBranchCode;
    }

    public String getApplCode() {
        return applCode;
    }

    public void setApplCode(String applCode) {
        this.applCode = applCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRelationCode() {
        return relationCode;
    }

    public void setRelationCode(String relationCode) {
        this.relationCode = relationCode;
    }
}
