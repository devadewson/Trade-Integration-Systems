package com.maybank.integratorapp.model.rest.fxrate.response;

import java.util.List;

public class FxRateResponse {
    private String dateCreated;
    private String respCode;
    private String respMsg;
    private List<FxRateData> respData;

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public List<FxRateData> getRespData() {
        return respData;
    }

    public void setRespData(List<FxRateData> respData) {
        this.respData = respData;
    }
}
