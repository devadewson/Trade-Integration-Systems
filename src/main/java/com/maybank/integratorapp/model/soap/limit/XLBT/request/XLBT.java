package com.maybank.integratorapp.model.soap.limit.XLBT.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.maybank.integratorapp.model.soap.accountinquiry.request.ChannelHeader;

@JacksonXmlRootElement(localName = "cms:XLBT")
public class XLBT {
    public XLBT() {
        this.channelHeader = new ChannelHeader();
        this.cMS_XLBTRequest = new CMS_XLBTRequest();
    }

    @JacksonXmlProperty(localName = "ChannelHeader")
    private ChannelHeader channelHeader;

    @JacksonXmlProperty(localName = "CMS_XLBTRequest")
    private CMS_XLBTRequest cMS_XLBTRequest;

    public ChannelHeader getChannelHeader() {
        return channelHeader;
    }

    public void setChannelHeader(ChannelHeader channelHeader) {
        this.channelHeader = channelHeader;
    }

    public CMS_XLBTRequest getcMS_XLBTRequest() {
        return cMS_XLBTRequest;
    }

    public void setcMS_XLBTRequest(CMS_XLBTRequest cMS_XLBTRequest) {
        this.cMS_XLBTRequest = cMS_XLBTRequest;
    }
}
