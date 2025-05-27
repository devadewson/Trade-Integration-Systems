package com.maybank.integratorapp.model.rest.AccountList.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maybank.integratorapp.model.rest.CustomerDetail.request.ChannelHeader;

public class AccountList {
    @JsonProperty("ChannelHeader")
    private ChannelHeader channelHeader;
    @JsonProperty("AccountListRequest")
    private AccountListRequest accountListRequest;

    public AccountList() {
        this.channelHeader = new ChannelHeader();
        this.accountListRequest = new AccountListRequest();
    }

    public ChannelHeader getChannelHeader() {
        return channelHeader;
    }

    public void setChannelHeader(ChannelHeader channelHeader) {
        this.channelHeader = channelHeader;
    }

    public AccountListRequest getAccountListRequest() {
        return accountListRequest;
    }

    public void setAccountListRequest(AccountListRequest accountListRequest) {
        this.accountListRequest = accountListRequest;
    }
}
