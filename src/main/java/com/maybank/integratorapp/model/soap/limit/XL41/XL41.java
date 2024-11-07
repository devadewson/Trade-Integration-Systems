package com.maybank.integratorapp.model.soap.limit.XL41;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.maybank.integratorapp.model.soap.accountinquiry.request.ChannelHeader;

public class XL41 {
    public XL41() {
        this.channelHeader = new ChannelHeader();
        this.cMS_XL41Request = new CMS_XL41Request();
    }

    @JacksonXmlProperty(localName = "ChannelHeader")
    private ChannelHeader channelHeader;

    @JacksonXmlProperty(localName = "CMS_XL41Request")
    private CMS_XL41Request cMS_XL41Request;

    public ChannelHeader getChannelHeader() {
        return channelHeader;
    }

    public void setChannelHeader(ChannelHeader channelHeader) {
        this.channelHeader = channelHeader;
    }

    public CMS_XL41Request getCMS_XL31Request() {
        return cMS_XL41Request;
    }

    public void setCMS_XL31Request(CMS_XL41Request CMS_XL41Request) {
        this.cMS_XL41Request = CMS_XL41Request;
    }
}
