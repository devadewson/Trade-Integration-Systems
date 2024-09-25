package com.maybank.integratorapp.model.rest.fxratelist.response;

import com.maybank.integratorapp.model.rest.fxrate.response.FxRateData;

import java.util.List;

public class FxRateListResponse {
    private String dateCreated;
    private String respCode;
    private String respMsg;
    private List<FxRateListData> respData;

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

    public List<FxRateListData> getRespData() {
        return respData;
    }

    public void setRespData(List<FxRateListData> respData) {
        this.respData = respData;
    }
}
