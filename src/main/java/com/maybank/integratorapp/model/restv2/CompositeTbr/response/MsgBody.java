package com.maybank.integratorapp.model.restv2.CompositeTbr.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MsgBody {
    @JsonProperty("TxnDtl")
    private Object txnDtl;

    @JsonProperty("Resp")
    private Resp[] resp;

    public Object getTxnDtl() {
        return txnDtl;
    }

    public void setTxnDtl(Object txnDtl) {
        this.txnDtl = txnDtl;
    }

    public Resp[] getResp() {
        return resp;
    }

    public void setResp(Resp[] resp) {
        this.resp = resp;
    }
}
