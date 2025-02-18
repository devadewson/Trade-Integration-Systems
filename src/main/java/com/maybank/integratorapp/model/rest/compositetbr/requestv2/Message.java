package com.maybank.integratorapp.model.rest.compositetbr.requestv2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maybank.integratorapp.util.DynamicTBRJsonSerializer;

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
