package com.maybank.integratorapp.model.restv2.CompositeTbr.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    @JsonProperty("Msg")
    private Msg msg;

    // Getters and setters
    public Msg getMsg() {
        return msg;
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }
}
