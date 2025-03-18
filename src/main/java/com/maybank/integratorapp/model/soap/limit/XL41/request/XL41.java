package com.maybank.integratorapp.model.soap.limit.XL41.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class XL41 {

    public XL41 () {
        this.channelHeader = new ChannelHeader();
        this.cmsXl41Request = new CMS_XL41Request();
    }

    @JacksonXmlProperty(localName = "ChannelHeader")
    private ChannelHeader channelHeader;

    @JacksonXmlProperty(localName = "CMS_XL41Request")
    private CMS_XL41Request cmsXl41Request;

    public ChannelHeader getChannelHeader() {
        return channelHeader;
    }

    public void setChannelHeader(ChannelHeader channelHeader) {
        this.channelHeader = channelHeader;
    }

    public CMS_XL41Request getCmsXl41Request() {
        return cmsXl41Request;
    }

    public void setCmsXl41Request(CMS_XL41Request cmsXl41Request) {
        this.cmsXl41Request = cmsXl41Request;
    }
}
