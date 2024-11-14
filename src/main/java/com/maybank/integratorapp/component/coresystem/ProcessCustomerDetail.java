package com.maybank.integratorapp.component.coresystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybank.integratorapp.data.repository.MsParameterRepository;
import com.maybank.integratorapp.model.mq.customerdetail.response.AddressDetail;
import com.maybank.integratorapp.model.mq.customerdetail.response.AddressDetails;
import com.maybank.integratorapp.model.mq.customerdetail.response.CustomerDetailsResponse;
import com.maybank.integratorapp.model.rest.AccountList.request.AccountList;
import com.maybank.integratorapp.model.rest.AccountList.request.AccountListRequest;
import com.maybank.integratorapp.model.rest.AccountList.request.AccountListWrapper;
import com.maybank.integratorapp.model.rest.AccountList.response.AccountListResponse;
import com.maybank.integratorapp.model.rest.AccountList.response.AccountListResponseData;
import com.maybank.integratorapp.model.rest.AccountList.response.AccountListResponseWrapper;
import com.maybank.integratorapp.model.rest.CustomerDetail.request.ChannelHeader;
import com.maybank.integratorapp.model.rest.CustomerDetail.request.CustomerInformation;
import com.maybank.integratorapp.model.rest.CustomerDetail.request.CustomerInformationRequest;
import com.maybank.integratorapp.model.rest.CustomerDetail.request.CustomerInformationWraper;
import com.maybank.integratorapp.model.rest.CustomerDetail.response.CustomerInformationResponse;
import com.maybank.integratorapp.model.rest.CustomerDetail.response.CustomerInformationResponseData;
import com.maybank.integratorapp.model.rest.CustomerDetail.response.CustomerInformationResponseWraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProcessCustomerDetail {

    @Autowired
    private MsParameterRepository parameterRepository;

    public CustomerInformationResponse getCustomerDetail(String gcifNo){
        CustomerDetailsResponse customerDetailsResponse = new CustomerDetailsResponse();
        CustomerInformationResponseData customerInformationResponseData = new CustomerInformationResponseData();
        CustomerInformationResponse customerInformationResponse = new CustomerInformationResponse();

        try {
            String apiUrl = parameterRepository.findValueByPrmKey("CustomerInformationRequest");
            ChannelHeader channelHeader = new ChannelHeader();
            channelHeader.setMessageID("TESTING");
            channelHeader.setBranchCode("270");
            channelHeader.setChannelID("M2U");
            channelHeader.setReference("M2U/123/2013");
            channelHeader.setTransactionDate("24-09-2024");
            channelHeader.setTransactionTime("10:18:43");

            CustomerInformationRequest customerInformationRequest = new CustomerInformationRequest();
            customerInformationRequest.setGCIFNo(gcifNo);

            CustomerInformation customerInformation = new CustomerInformation();
            customerInformation.setChannelHeader(channelHeader);
            customerInformation.setCustomerInformationRequest(customerInformationRequest);

            CustomerInformationWraper customerInformationWraper = new CustomerInformationWraper();
            customerInformationWraper.setCustomerInformation(customerInformation);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the HttpEntity object with request body and headers
            HttpEntity<CustomerInformation> request = new HttpEntity<>(customerInformation, headers);
            RestTemplate restTemplate = new RestTemplate();

            HttpEntity<CustomerInformationWraper> reqWrapper = new HttpEntity<CustomerInformationWraper>(
                    customerInformationWraper, headers);

            String response = restTemplate.postForObject(apiUrl, reqWrapper, String.class);
            CustomerInformationResponseWraper responseWraper = new ObjectMapper().readValue(response,
                    CustomerInformationResponseWraper.class);

            customerInformationResponse = responseWraper.getCustomerInformationResponse();
            customerInformationResponseData = customerInformationResponse.getCustomerInformationResponseData();
            customerDetailsResponse.setFullName(customerInformationResponseData.getFullName());
            customerDetailsResponse.setCustomerNumber(customerInformationResponseData.getGCIFNo());

            AddressDetails addressDetails = new AddressDetails();
            AddressDetail detailAddress = new AddressDetail();
            String fullAddress = customerInformationResponseData.getAddressLine1() + " " +
                    customerInformationResponseData.getAddressLine2() + " " +
                    customerInformationResponseData.getAddressLine3();
            detailAddress.setAddressType(fullAddress);

            addressDetails.setAddressDetail(detailAddress);
            customerDetailsResponse.setAddressDetails(addressDetails);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return customerInformationResponse;
    }

    public AccountListResponse getAccListByGcifNo(String gcifNo) {
        AccountListResponse accountListResponse = new AccountListResponse();
        AccountListResponseData accountListResponseData = new AccountListResponseData();
        String res = "";
        try {
            String apiUrl = parameterRepository.findValueByPrmKey("CustomerInformationRequest");

            ChannelHeader channelHeader = new ChannelHeader();
            channelHeader.setMessageID("TESTING");
            channelHeader.setBranchCode("270");
            channelHeader.setChannelID("M2U");
            channelHeader.setReference("M2U/123/2013");
            channelHeader.setTransactionDate("24-09-2024");
            channelHeader.setTransactionTime("10:18:43");

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
            res = accountListResponseData.getAccountData().get(0).getCifNo();

            System.out.println("===========================res=================================");
            System.out.println(res);
            System.out.println("============================================================\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return accountListResponse;
    }
}
