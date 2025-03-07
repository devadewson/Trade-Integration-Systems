package com.maybank.integratorapp.model.restv2.AccountList.request;

import com.fasterxml.jackson.annotation.JsonProperty;

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
