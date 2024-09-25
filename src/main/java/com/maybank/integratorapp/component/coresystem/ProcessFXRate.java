package com.maybank.integratorapp.component.coresystem;

import com.maybank.integratorapp.model.mq.fxratefcc.response.ExchangeRateRecord;
import com.maybank.integratorapp.model.rest.fxrate.request.FxRateRequest;
import com.maybank.integratorapp.model.rest.fxrate.response.FxRateResponse;
import com.maybank.integratorapp.model.rest.fxratelist.request.FxRateListRequest;
import com.maybank.integratorapp.model.rest.fxratelist.response.FxRateListData;
import com.maybank.integratorapp.model.rest.fxratelist.response.FxRateListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Component
public class ProcessFXRate {

//    @Autowired
//    private RestTemplate restTemplate;
    public ExchangeRateRecord getExchangeRateRecords(String base, String currency){
        ExchangeRateRecord finalData = new ExchangeRateRecord();

        try {
            String apiUrl = "http://10.230.83.121:8282/trapi/getKurs";
            FxRateRequest req = new FxRateRequest();
            req.setCcy1("USD");
            req.setCcy2("IDR");
            req.setTenor("TODAY");
            req.setClientName("M2U_270");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the HttpEntity object with request body and headers
            HttpEntity<FxRateRequest> request = new HttpEntity<>(req, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<FxRateResponse> response = restTemplate.postForEntity(apiUrl,request, FxRateResponse.class);

            if(response.hasBody()){
                System.out.println(response.getBody().getRespCode());
            }
        }catch (Exception e){
            throw e;
        }

        return finalData;
    }

    public List<FxRateListData> getAllFxRate(){
        List<FxRateListData> finalData = new ArrayList<FxRateListData>();

        try{

            String apiUrl = "http://10.230.83.121:8282/trapi/getListCcy";
            FxRateListRequest req = new FxRateListRequest();
            req.setCcy1("");
            req.setCcy2("");
            req.setTenor("TODAY");
            req.setClientName("M2U_270");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the HttpEntity object with request body and headers
            HttpEntity<FxRateListRequest> request = new HttpEntity<>(req, headers);
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<FxRateListResponse> response = restTemplate.postForEntity(apiUrl,request, FxRateListResponse.class);

            if(response.hasBody()){
                System.out.println(response.getBody().getRespCode());

                FxRateListResponse responseList = response.getBody();

                finalData = responseList.getRespData();
            }


        }catch (Exception e){
            throw e;
        }

        return finalData;


    }

}
