package com.maybank.integratorapp.model.rest.compositetbr.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maybank.integratorapp.util.DynamicTBRJsonSerializer;

import java.util.ArrayList;
import java.util.List;

@JsonSerialize(using = DynamicTBRJsonSerializer.class)
public class ExecuteCompositeTransactionRequest {
    public ExecuteCompositeTransactionRequest(){
        this.TBRData = new ArrayList<>();
    }
    @JsonProperty("TransactionName")
    private String TransactionName;
    @JsonProperty("TBRData")
    private List<Object> TBRData;

    public String getTransactionName() {
        return TransactionName;
    }

    public void setTransactionName(String transactionName) {
        TransactionName = transactionName;
    }

    public List<Object> getTBRData() {
        return TBRData;
    }

    public void setTBRData(List<Object> TBRData) {
        this.TBRData = TBRData;
    }
}
