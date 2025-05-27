package com.maybank.integratorapp.model.restv2.CompositeTbr.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maybank.integratorapp.util.NewArchTBRJsonSerializer;

import java.util.List;

@JsonSerialize(using = NewArchTBRJsonSerializer.class)
public class MsgBody {
    @JsonProperty("TBRData")
    private List<Object> tbrData;

    // Getters and setters
    public List<Object> getTbrData() {
        return tbrData;
    }

    public void setTbrData(List<Object> tbrData) {
        this.tbrData = tbrData;
    }
}

