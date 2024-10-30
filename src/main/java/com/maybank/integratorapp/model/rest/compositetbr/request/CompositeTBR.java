package com.maybank.integratorapp.model.rest.compositetbr.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompositeTBR {
    public CompositeTBR(){
        this.channelHeader = new ChannelHeader();
        this.executeCompositeTransactionRequest = new ExecuteCompositeTransactionRequest();
    }
    @JsonProperty("ChannelHeader")
    private ChannelHeader channelHeader;
    @JsonProperty("executeCompositeTransactionRequest")
    private ExecuteCompositeTransactionRequest executeCompositeTransactionRequest;

    public ChannelHeader getChannelHeader() {
        return channelHeader;
    }

    public void setChannelHeader(ChannelHeader channelHeader) {
        this.channelHeader = channelHeader;
    }

    public ExecuteCompositeTransactionRequest getExecuteCompositeTransactionRequest() {
        return executeCompositeTransactionRequest;
    }

    public void setExecuteCompositeTransactionRequest(ExecuteCompositeTransactionRequest executeCompositeTransactionRequest) {
        this.executeCompositeTransactionRequest = executeCompositeTransactionRequest;
    }
}
