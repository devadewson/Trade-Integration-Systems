package com.maybank.integratorapp.model.soap.limit.XL40.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class XL40 {

    public XL40() {
        this.channelHeader = new ChannelHeader();
        this.cmsXl40Request = new CMS_XL40Request();
    }

    @JacksonXmlProperty(localName = "ChannelHeader")
    private ChannelHeader channelHeader;

    @JacksonXmlProperty(localName = "CMS_XL40Request")
    private CMS_XL40Request cmsXl40Request;

    public ChannelHeader getChannelHeader() {
        return channelHeader;
    }

    public void setChannelHeader(ChannelHeader channelHeader) {
        this.channelHeader = channelHeader;
    }

    public CMS_XL40Request getCmsXl40Request() {
        return cmsXl40Request;
    }

    public void setCmsXl40Request(CMS_XL40Request cmsXl40Request) {
        this.cmsXl40Request = cmsXl40Request;
    }
}
