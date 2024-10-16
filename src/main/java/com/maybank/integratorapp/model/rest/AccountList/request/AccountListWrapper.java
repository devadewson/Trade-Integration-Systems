package com.maybank.integratorapp.model.rest.AccountList.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountListWrapper {
    @JsonProperty("AccountList")
    private AccountList accountList;

    public AccountListWrapper() {
        this.accountList = new AccountList();
    }

    public AccountList getAccountList() {
        return accountList;
    }

    public void setAccountList(AccountList accountList) {
        this.accountList = accountList;
    }
}
