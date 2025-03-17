package com.maybank.integratorapp.model.soap.limit.XL31.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class XL31 {
    public XL31 (){
        this.cmsXl31Request = new CMS_XL31Request();
        this.channelHeader = new ChannelHeader();
    }
    @JacksonXmlProperty(localName = "ChannelHeader")
    private ChannelHeader channelHeader ;

    @JacksonXmlProperty(localName = "CMS_XL31Request")
    private CMS_XL31Request cmsXl31Request  ;

    public ChannelHeader getChannelHeader() {
        return channelHeader;
    }

    public void setChannelHeader(ChannelHeader channelHeader) {
        this.channelHeader = channelHeader;
    }

    public CMS_XL31Request getCmsXl31Request() {
        return cmsXl31Request;
    }

    public void setCmsXl31Request(CMS_XL31Request cmsXl31Request) {
        this.cmsXl31Request = cmsXl31Request;
    }
}
