package com.maybank.integratorapp.model.soap.limit.XL31;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.maybank.integratorapp.model.soap.accountinquiry.request.ChannelHeader;

public class XL31 {
    public XL31() {
        this.channelHeader = new ChannelHeader();
        this.cMS_XL31Request = new CMS_XL31Request();
    }

    @JacksonXmlProperty(localName = "ChannelHeader")
    private ChannelHeader channelHeader;

    @JacksonXmlProperty(localName = "CMS_XL31Request")
    private CMS_XL31Request cMS_XL31Request;

    public ChannelHeader getChannelHeader() {
        return channelHeader;
    }

    public void setChannelHeader(ChannelHeader channelHeader) {
        this.channelHeader = channelHeader;
    }

    public CMS_XL31Request getCMS_XL31Request() {
        return cMS_XL31Request;
    }

    public void setCMS_XL31Request(CMS_XL31Request CMS_XL31Request) {
        this.cMS_XL31Request = CMS_XL31Request;
    }
}
