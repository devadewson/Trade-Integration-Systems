package com.maybank.integratorapp.model.rest.compositetbr.requestv2;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Msg {
    @JsonProperty("MsgHeader")
    private MsgHeader msgHeader;
    @JsonProperty("MsgBody")

    private MsgBody msgBody;

    // Getters and setters
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
