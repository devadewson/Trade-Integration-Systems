package com.maybank.integratorapp.model.soap.accountinquiry.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class AccountInquiry {
    public AccountInquiry(){
        this.accountInquiryRequest = new AccountInquiryRequest();
        this.channelHeader = new ChannelHeader();
    }
    @JacksonXmlProperty(localName = "ChannelHeader")
    private ChannelHeader channelHeader;

    @JacksonXmlProperty(localName = "AccountInquiryRequest")
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
}

