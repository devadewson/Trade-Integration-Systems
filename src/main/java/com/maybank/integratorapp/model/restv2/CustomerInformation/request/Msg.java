package com.maybank.integratorapp.model.restv2.CustomerInformation.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Msg {
    @JsonProperty("MsgHeader")
    private MsgHeader msgHeader;
    @JsonProperty("MsgBody")
    private MsgBody msgBody;

    public Msg() {
        this.msgHeader = new MsgHeader() ;
        this.msgBody = new MsgBody() ;
    }

    public MsgHeader getMsgHeader() {
        return msgHeader;
    }

    public void setMsgHeader(MsgHeader msgHeader) {
        this.msgHeader = msgHeader;
    }

    public MsgBody getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(MsgBody msgBody) {
        this.msgBody = msgBody;
    }
}
