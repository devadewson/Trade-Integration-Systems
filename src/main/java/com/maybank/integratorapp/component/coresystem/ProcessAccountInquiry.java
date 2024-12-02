package com.maybank.integratorapp.component.coresystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybank.integratorapp.data.repository.MsParameterRepository;
import com.maybank.integratorapp.model.rest.AccountInquiry.request.*;
import com.maybank.integratorapp.model.rest.AccountInquiry.response.AccountInquiryResponse;
import com.maybank.integratorapp.model.rest.AccountInquiry.response.AccountInquiryResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ProcessAccountInquiry {
    @Autowired
    MsParameterRepository parameterRepository;

    public AccountInquiryResponse getAccInqWithVar(String accNo, String branch, String curr){
        AccountInquiryResponse res = new AccountInquiryResponse();
        try {
            String apiUrl = parameterRepository.findValueByPrmKey("AccountInquiryRequest");
            String branchCode = parameterRepository.findValueByPrmKey("ChannelHeaderBranchCode");
            String channelId = parameterRepository.findValueByPrmKey("ChannelHeaderChannelId");
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
            ChannelHeader channelHeader = new ChannelHeader();
            channelHeader.setMessageID("TESTING");
            channelHeader.setBranchCode(branchCode);
            channelHeader.setChannelID(channelId);
            channelHeader.setClientSupervisorID("AA");
            channelHeader.setClientUserID("?");
            channelHeader.setReference("XXXX-XXXX-XXXX");
            channelHeader.setReversalSequenceNo("112233");
            channelHeader.setTransactionDate(date);
            channelHeader.setTransactionTime(time);

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

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return res;
    }
}
