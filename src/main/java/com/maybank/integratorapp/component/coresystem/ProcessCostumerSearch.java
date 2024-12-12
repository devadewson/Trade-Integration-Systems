package com.maybank.integratorapp.component.coresystem;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybank.integratorapp.data.entity.MsCompanyData;
import com.maybank.integratorapp.data.repository.MsCompanyDataRepository;
import com.maybank.integratorapp.data.repository.MsParameterRepository;
import com.maybank.integratorapp.model.mq.customersearch.response.CustomerSearchResult;
import com.maybank.integratorapp.model.rest.AccountList.request.AccountList;
import com.maybank.integratorapp.model.rest.AccountList.request.AccountListRequest;
import com.maybank.integratorapp.model.rest.AccountList.request.AccountListWrapper;
import com.maybank.integratorapp.model.rest.AccountList.response.AccountListResponse;
import com.maybank.integratorapp.model.rest.AccountList.response.AccountListResponseData;
import com.maybank.integratorapp.model.rest.AccountList.response.AccountListResponseWrapper;
import com.maybank.integratorapp.model.rest.CustomerSearchh.request.*;
import com.maybank.integratorapp.model.rest.CustomerSearchh.response.CustomerInformationResponseWraper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public  class ProcessCostumerSearch {
    @Autowired
    MsParameterRepository parameterRepository;

    @Autowired
    MsCompanyDataRepository companyDataRepository;

    public CustomerSearchResult getCustomerSearchResult(String gcifNo) {
        CustomerSearchResult customerSearchResult = new CustomerSearchResult();

        String api = parameterRepository.findValueByPrmKey("CustomerSearchRequest");

        try {
            // Buat request CustomerInformation
            CustomerInformation req = new CustomerInformation();
            CustomerInformationWraper wraper = new CustomerInformationWraper();

            String branchCode = parameterRepository.findValueByPrmKey("ChannelHeaderBranchCode");
            String channelId = parameterRepository.findValueByPrmKey("ChannelHeaderChannelId");
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
            ChannelHeader channelHeader = new ChannelHeader();

            req.getChannelHeader().setMessageID("TESTING");
            req.getChannelHeader().setBranchCode(branchCode);
            req.getChannelHeader().setChannelID(channelId);
            req.getChannelHeader().setReference("");
            req.getChannelHeader().setSequenceNo("");
            req.getChannelHeader().setTransactionDate(date);
            req.getChannelHeader().setTransactionTime(time);
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
                    MsCompanyData companyData = companyDataRepository.findByGcif(gcifNo);

                    String responseString = EntityUtils.toString(response.getEntity());
                    if(companyData == null){
                        companyData = new MsCompanyData();
                        companyData.setGcifno(gcifNo);
                        companyData.setCreated_date(new Date());
                    }
                    
                    companyData.setCustInfoData(responseString);
                    companyData.setUpdated_date(new Date());

                    companyData = companyDataRepository.save(companyData);

                    CustomerInformationResponseWraper res = objectMapper.readValue(responseString, CustomerInformationResponseWraper.class);

                    AccountListResponse accountListResponse = getAccListByGcifNo(gcifNo,companyData);
                    if(accountListResponse.getAccountListResponseData().getAccountData()!= null) {
                        //Maaping respon ESB to respon FTI
                        customerSearchResult.setGroup("ADIMAH");
                        customerSearchResult.setAccountOfficer("*");
                        customerSearchResult.setBlocked("N");
                        customerSearchResult.setCustomerMnemonic(accountListResponse.getAccountListResponseData().getAccountData().get(0).getCifNo());
                        customerSearchResult.setCustomerNumber(res.getCustomerInformationResponse().getCustomerInformationResponseData().getGCIFNo());
                        customerSearchResult.setFullName(res.getCustomerInformationResponse().getCustomerInformationResponseData().getFullName());
                        customerSearchResult.setCountryOfResidence(res.getCustomerInformationResponse().getCustomerInformationResponseData().getNationality());

                        String Address = res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine1() +" "+ res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine2() +" "+
                                res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine3() +" "+ res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine4()+" " +
                                res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine5() +" "+ res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine6()+" " +
                                res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine6()+" " + res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine7()+" " +
                                res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine8() +" "+ res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine9()+" " +
                                res.getCustomerInformationResponse().getCustomerInformationResponseData().getAddressLine10();
                        Address = Address.substring(0,59);
                        customerSearchResult.setLocation(Address);

                        return customerSearchResult;
                    }


                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return customerSearchResult;
    }


    public AccountListResponse getAccListByGcifNo(String gcifNo, MsCompanyData companyData) {
        AccountListResponse accountListResponse = new AccountListResponse();
        AccountListResponseData accountListResponseData = new AccountListResponseData();
        String cifNo = "";
        try {
            String apiUrl = parameterRepository.findValueByPrmKey("CustomerInformationRequest");
            String branchCode = parameterRepository.findValueByPrmKey("ChannelHeaderBranchCode");
            String channelId = parameterRepository.findValueByPrmKey("ChannelHeaderChannelId");
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

            com.maybank.integratorapp.model.rest.CustomerDetail.request.ChannelHeader channelHeader = new com.maybank.integratorapp.model.rest.CustomerDetail.request.ChannelHeader();
            channelHeader.setMessageID("TESTING");
            channelHeader.setBranchCode(branchCode);
            channelHeader.setChannelID(channelId);
            channelHeader.setReference("M2U/123/2013");
            channelHeader.setTransactionDate(date);
            channelHeader.setTransactionTime(time);

            AccountListRequest accountListRequest = new AccountListRequest();
            accountListRequest.setGCIFNo(gcifNo);

            AccountList accountList = new AccountList();
            accountList.setAccountListRequest(accountListRequest);
            accountList.setChannelHeader(channelHeader);

            AccountListWrapper accountListWrapper = new AccountListWrapper();
            accountListWrapper.setAccountList(accountList);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the HttpEntity object with request body and headers
            HttpEntity<AccountListWrapper> reqWrapper = new HttpEntity<AccountListWrapper>(
                    accountListWrapper, headers);
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.postForObject(apiUrl, reqWrapper, String.class);

            AccountListResponseWrapper accountListResponseWrapper = new ObjectMapper().readValue(response,
                    AccountListResponseWrapper.class);

            accountListResponse = accountListResponseWrapper.getAccountListResponse();
            accountListResponseData = accountListResponse.getAccountListResponseData();
            if(accountListResponseData.getAccountData() != null){
                cifNo = accountListResponseData.getAccountData().get(0).getCifNo();

                companyData.setCifno(cifNo);
                companyData.setAccInfoData(response);
                companyData.setUpdated_date(new Date());
                companyDataRepository.save(companyData);
            }

            System.out.println("===========================cifNo=================================");
            System.out.println(cifNo);
            System.out.println("============================================================\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return accountListResponse;
    }

    }




