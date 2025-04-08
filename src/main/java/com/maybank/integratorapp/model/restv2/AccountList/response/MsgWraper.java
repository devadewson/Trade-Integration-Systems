package com.maybank.integratorapp.model.restv2.AccountList.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgWraper {
    public MsgWraper() {this.msg = new Msg();}

    @JsonProperty("Msg")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private Msg msg ;

    public Msg getMsg() {
        return msg;
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }
}
