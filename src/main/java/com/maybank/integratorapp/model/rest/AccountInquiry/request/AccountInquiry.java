package com.maybank.integratorapp.model.rest.AccountInquiry.request;

import com.fasterxml.jackson.annotation.JsonProperty;
public class AccountInquiry {
    public AccountInquiry(){
        this.accountInquiryRequest = new AccountInquiryRequest();
        this.channelHeader = new ChannelHeader();
    }
    @JsonProperty("ChannelHeader")
    private ChannelHeader channelHeader;

    @JsonProperty("AccountInquiryRequest")
    private AccountInquiryRequest accountInquiryRequest;

    public ChannelHeader getChannelHeader() {
        return channelHeader;
    }

    public void setChannelHeader(ChannelHeader channelHeader) {
        this.channelHeader = channelHeader;
    }

    public AccountInquiryRequest getAccountInquiryRequest() {
        return accountInquiryRequest;
    }

    public void setAccountInquiryRequest(AccountInquiryRequest accountInquiryRequest) {
        this.accountInquiryRequest = accountInquiryRequest;
    }

    @Override
    public String toString() {
        return "AccountInquiry{" +
                "ChannelHeader=" + channelHeader + ", "+
                ", AccountInquiryRequest=" + accountInquiryRequest +
                '}';
    }
}
