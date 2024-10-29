package com.maybank.integratorapp.component.coresystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybank.integratorapp.data.repository.MsParameterRepository;
import com.maybank.integratorapp.model.rest.AccountInquiry.request.*;
import com.maybank.integratorapp.model.rest.AccountInquiry.response.AccountInquiryResponse;
import com.maybank.integratorapp.model.rest.AccountInquiry.response.AccountInquiryResponseWrapper;
import com.maybank.integratorapp.model.rest.AccountInquiry.response.accountInquiryResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProcessAccountInquiry {
    @Autowired
    MsParameterRepository parameterRepository;

    public AccountInquiryResponse getAccInq(){
        AccountInquiryResponse res = new AccountInquiryResponse();
        accountInquiryResponseData responseData = new accountInquiryResponseData();

        try {
            String apiUrl = parameterRepository.findValueByPrmKey("CustomerInformationRequest");;

            ChannelHeader channelHeader = new ChannelHeader();
            channelHeader.setMessageID("TESTING");
            channelHeader.setBranchCode("003");
            channelHeader.setChannelID("RCMS");
            channelHeader.setClientSupervisorID("AA");
            channelHeader.setClientUserID("?");
            channelHeader.setReference("XXXX-XXXX-XXXX");
            channelHeader.setReversalSequenceNo("112233");
            channelHeader.setTransactionDate("24-09-2024");
            channelHeader.setTransactionTime("10:18:43");

            AccountInquiryRequest accountInquiryRequest = new AccountInquiryRequest();
            accountInquiryRequest.setAccountNo("1001146880");
            accountInquiryRequest.setAccountBranchCode("001");
            accountInquiryRequest.setAccountCurrency("016");

            AccountInquiry accountInquiry = new AccountInquiry();
            accountInquiry.setChannelHeader(channelHeader);
            accountInquiry.setAccountInquiryRequest(accountInquiryRequest);

            AccountInquiryWrapper accountInquiryWrapper = new AccountInquiryWrapper();
            accountInquiryWrapper.setAccountInquiry(accountInquiry);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the HttpEntity object with request body and headers
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<AccountInquiryWrapper> reqWrapper = new HttpEntity<AccountInquiryWrapper>(accountInquiryWrapper, headers);
            String response = restTemplate.postForObject(apiUrl, reqWrapper, String.class);

            AccountInquiryResponseWrapper responseWrapper = new ObjectMapper().readValue(response, AccountInquiryResponseWrapper.class);

            res = responseWrapper.getAccountInquiryResponse();
            responseData = res.getAccountInquiryResponseData();

        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return res;
    }
    public static String jsonBody() {
        try {
            // Create the ChannelHeader map
            Map<String, String> channelHeader = new HashMap<>();
            channelHeader.put("messageID", "asdasdasd");
            channelHeader.put("branchCode", "003");
            channelHeader.put("channelID", "RCMS");
            channelHeader.put("clientSupervisorID", "AA");
            channelHeader.put("clientUserID", "?");
            channelHeader.put("reference", "XXXX-XXXX-XXXX");
            channelHeader.put("sequenceno", "112233");
            channelHeader.put("transactiondate", "24-09-2024");
            channelHeader.put("transactiontime", "10:18:43");

            // Create the AccountInquiryRequest map
            Map<String, String> accountInquiryRequest = new HashMap<>();
            accountInquiryRequest.put("accountNo", "1001146880");
            accountInquiryRequest.put("accountBranchCode", "001");
            accountInquiryRequest.put("accountCurrency", "016");

            // Combine them into AccountInquiry map
            Map<String, Object> accountInquiry = new HashMap<>();
            accountInquiry.put("ChannelHeader", channelHeader);
            accountInquiry.put("AccountInquiryRequest", accountInquiryRequest);

            // Final JSON object containing AccountInquiry
            Map<String, Object> jsonBody = new HashMap<>();
            jsonBody.put("AccountInquiry", accountInquiry);

            // Convert the Map to JSON using ObjectMapper
            return new ObjectMapper().writeValueAsString(jsonBody);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    public  AccountInquiryResponse getAccInqFromQ(String message){
        AccountInquiryResponse res = new AccountInquiryResponse();
        accountInquiryResponseData responseData = new accountInquiryResponseData();
        try{
            String apiUrl = parameterRepository.findValueByPrmKey("CustomerInformationRequest");;
            System.out.println("===========================test=================================");
            System.out.println(message);
            System.out.println("============================================================");

            ChannelHeader channelHeader = new ChannelHeader();
            AccountInquiryRequest accountInquiryRequest = new AccountInquiryRequest();
            AccountInquiry accountInquiry = new ObjectMapper().readValue(message, AccountInquiry.class);

            AccountInquiryWrapper accountInquiryWrapper = new AccountInquiryWrapper();
            accountInquiryWrapper.setAccountInquiry(accountInquiry);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the HttpEntity object with request body and headers
            HttpEntity<AccountInquiry> request = new HttpEntity<>(accountInquiry, headers);
            RestTemplate restTemplate = new RestTemplate();

            HttpEntity<AccountInquiryWrapper> reqWrapper = new HttpEntity<AccountInquiryWrapper>(accountInquiryWrapper, headers);
            String response = restTemplate.postForObject(apiUrl, reqWrapper, String.class);

            AccountInquiryResponseWrapper responseWrapper = new ObjectMapper().readValue(response, AccountInquiryResponseWrapper.class);

            res = responseWrapper.getAccountInquiryResponse();
            responseData = res.getAccountInquiryResponseData();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return res;

    }

    public AccountInquiryResponse getAccInqWithVar(String accNo, String branch, String curr){
        AccountInquiryResponse res = new AccountInquiryResponse();
        accountInquiryResponseData responseData = new accountInquiryResponseData();

        try {
            String apiUrl = "http://10.235.66.96:7800/accountservicesapi/v1/AccountServices";

            ChannelHeader channelHeader = new ChannelHeader();
            channelHeader.setMessageID("TESTING");
            channelHeader.setBranchCode("003");
            channelHeader.setChannelID("RCMS");
            channelHeader.setClientSupervisorID("AA");
            channelHeader.setClientUserID("?");
            channelHeader.setReference("XXXX-XXXX-XXXX");
            channelHeader.setReversalSequenceNo("112233");
            channelHeader.setTransactionDate("24-09-2024");
            channelHeader.setTransactionTime("10:18:43");

            AccountInquiryRequest accountInquiryRequest = new AccountInquiryRequest();
            accountInquiryRequest.setAccountNo(accNo);
            accountInquiryRequest.setAccountBranchCode(branch);
            accountInquiryRequest.setAccountCurrency(curr);

            AccountInquiry accountInquiry = new AccountInquiry();
            accountInquiry.setChannelHeader(channelHeader);
            accountInquiry.setAccountInquiryRequest(accountInquiryRequest);

            AccountInquiryWrapper accountInquiryWrapper = new AccountInquiryWrapper();
            accountInquiryWrapper.setAccountInquiry(accountInquiry);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the HttpEntity object with request body and headers
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<AccountInquiryWrapper> reqWrapper = new HttpEntity<AccountInquiryWrapper>(accountInquiryWrapper, headers);
            String response = restTemplate.postForObject(apiUrl, reqWrapper, String.class);

            System.out.println("===========================response=================================");
            System.out.println(response);
            System.out.println("============================================================\n");
            AccountInquiryResponseWrapper responseWrapper = new ObjectMapper().readValue(response, AccountInquiryResponseWrapper.class);

            res = responseWrapper.getAccountInquiryResponse();
            responseData = res.getAccountInquiryResponseData();

        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return res;
    }
}
