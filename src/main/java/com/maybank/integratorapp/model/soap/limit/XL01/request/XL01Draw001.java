package com.maybank.integratorapp.model.soap.limit.XL01.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class XL01Draw001 {
    public XL01Draw001(){
        this.cmsXl01Draw001Request = new CMS_XL01Draw001Request();
        this.channelHeader = new ChannelHeader();
    }
    @JacksonXmlProperty(localName = "ChannelHeader")
    private ChannelHeader channelHeader  ;

    @JacksonXmlProperty(localName = "CMS_XL01Draw001Request")
    private CMS_XL01Draw001Request cmsXl01Draw001Request  ;

    public ChannelHeader getChannelHeader() {
        return channelHeader;
    }

    public void setChannelHeader(ChannelHeader channelHeader) {
        this.channelHeader = channelHeader;
    }

    public CMS_XL01Draw001Request getCmsXl01Draw001Request() {
        return cmsXl01Draw001Request;
    }

    public void setCmsXl01Draw001Request(CMS_XL01Draw001Request cmsXl01Draw001Request) {
        this.cmsXl01Draw001Request = cmsXl01Draw001Request;
    }
}
