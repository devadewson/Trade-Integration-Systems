package com.maybank.integratorapp.model.soap.XLBT.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.maybank.integratorapp.model.soap.XLBT.request.CMS_XLBTRequest;
import com.maybank.integratorapp.model.soap.XLBT.request.ChannelHeader;


public class XLBT {
    public XLBT(){
        this.cmsXlbtRequest = new CMS_XLBTRequest();
        this.channelHeader = new ChannelHeader();
    }
    @JacksonXmlProperty(localName = "ChannelHeader")
    private ChannelHeader channelHeader ;

    @JacksonXmlProperty(localName = "CMS_XLBTRequest")
    private CMS_XLBTRequest cmsXlbtRequest ;

    public ChannelHeader getChannelHeader() {
        return channelHeader;
    }

    public void setChannelHeader(ChannelHeader channelHeader) {
        this.channelHeader = channelHeader;
    }

    public CMS_XLBTRequest getCmsXlbtRequest() {
        return cmsXlbtRequest;
    }

    public void setCmsXlbtRequest(CMS_XLBTRequest cmsXlbtRequest) {
        this.cmsXlbtRequest = cmsXlbtRequest;
    }
}
