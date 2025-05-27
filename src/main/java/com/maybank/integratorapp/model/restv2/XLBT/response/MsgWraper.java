package com.maybank.integratorapp.model.restv2.XLBT.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maybank.integratorapp.model.restv2.AccountInquiry.response.MsgBody;
import com.maybank.integratorapp.model.restv2.AccountInquiry.response.MsgHeader;

public class MsgWraper {
    public MsgWraper( ) {
        this.msg = new com.maybank.integratorapp.model.restv2.XLBT.response.Msg();
    }

    @JsonProperty("Msg")
    private com.maybank.integratorapp.model.restv2.XLBT.response.Msg msg;


    public Msg getMsg() {
        return msg;
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }
}

