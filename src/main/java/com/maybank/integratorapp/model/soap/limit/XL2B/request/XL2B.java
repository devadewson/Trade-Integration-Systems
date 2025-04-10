package com.maybank.integratorapp.model.soap.limit.XL2B.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;


public class XL2B {
    public XL2B(){
        this.cmsXl2BRequest = new CMS_XL2BRequest();
        this.channelHeader = new ChannelHeader();
    }
    @JacksonXmlProperty(localName = "ChannelHeader")
    private ChannelHeader channelHeader ;

    @JacksonXmlProperty(localName = "CMS_XL2BRequest")
    private CMS_XL2BRequest cmsXl2BRequest  ;

    public ChannelHeader getChannelHeader() {
        return channelHeader;
    }

    public void setChannelHeader(ChannelHeader channelHeader) {
        this.channelHeader = channelHeader;
    }

    public CMS_XL2BRequest getCmsXl2BRequest() {
        return cmsXl2BRequest;
    }

    public void setCmsXl2BRequest(CMS_XL2BRequest cmsXl2BRequest) {
        this.cmsXl2BRequest = cmsXl2BRequest;
    }
}
