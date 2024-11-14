package com.maybank.integratorapp.component.coresystem;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybank.integratorapp.data.repository.MsParameterRepository;
import com.maybank.integratorapp.model.mq.customersearch.response.CustomerSearchResult;
import com.maybank.integratorapp.model.rest.CustomerSearchh.request.*;
import com.maybank.integratorapp.model.rest.CustomerSearchh.response.CustomerInformationResponseWraper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public  class ProcessCostumerSearch {
    @Autowired
    MsParameterRepository parameterRepository;
    public CustomerSearchResult getCustomerSearchResult(String gcifNo) {
        CustomerSearchResult customerSearchResult = new CustomerSearchResult();

        String api = parameterRepository.findValueByPrmKey("CustomerSearchRequest");

        try {
            // Buat request CustomerInformation
            CustomerInformation req = new CustomerInformation();
            CustomerInformationWraper wraper = new CustomerInformationWraper();

            req.getChannelHeader().setMessageID("");
            req.getChannelHeader().setBranchCode("");
            req.getChannelHeader().setChannelID("");
            req.getChannelHeader().setReference("");
            req.getChannelHeader().setSequenceNo("");
            req.getChannelHeader().setTransactionDate("");
            req.getChannelHeader().setTransactionTime("");
            req.getCustomerInformationRequest().setGCIFNo(gcifNo);

            wraper.setCustomerInformation(req);

            // Konversi request ke JSON

            ObjectMapper objectMapper = new ObjectMapper();

            // how to not double json
            objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

            // String Reqjson = objectMapper.writeValueAsString(body);
            String json = objectMapper.writeValueAsString(wraper);

            // Kirim request ke API eksternal
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(api);
                httpPost.setHeader("Content-Type", "application/json");
                httpPost.setEntity(new StringEntity(json, "UTF-8"));

                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    String responseString = EntityUtils.toString(response.getEntity());

                    CustomerInformationResponseWraper res = objectMapper.readValue(responseString, CustomerInformationResponseWraper.class);

                   //Maaping respon ESB to respon FTI
                    customerSearchResult.setCustomerNumber(res.getCustomerInformationResponse().getCustomerInformationResponseData().getGCIFNo());
                    customerSearchResult.setFullName(res.getCustomerInformationResponse().getCustomerInformationResponseData().getFullName());
                    customerSearchResult.setCountryOfResidence(res.getCustomerInformationResponse().getCustomerInformationResponseData().getNationality());

                    String Address = res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine1() +" "+ res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine2() +" "+
                            res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine3() +" "+ res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine4()+" " +
                            res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine5() +" "+ res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine6()+" " +
                            res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine6()+" " + res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine7()+" " +
                            res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine8() +" "+ res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine9()+" " +
                            res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine10();

                    customerSearchResult.setLocation(Address);

                    return customerSearchResult;

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    }


