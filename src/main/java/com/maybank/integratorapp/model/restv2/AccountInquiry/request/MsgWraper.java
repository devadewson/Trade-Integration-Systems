package com.maybank.integratorapp.model.restv2.AccountInquiry.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maybank.integratorapp.model.rest.CustomerSearchh.request.CustomerInformation;

public class MsgWraper {
    public MsgWraper() {this.msg = new Msg();}

    @JsonProperty("Msg")
    private Msg msg ;

    public Msg getMsg() {
        return msg;
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }
}
