package com.maybank.integratorapp.component.system.messageprocessor;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.maybank.integratorapp.data.entity.MsCompanyData;
import com.maybank.integratorapp.data.repository.MsCompanyDataRepository;
import com.maybank.integratorapp.data.repository.MsParameterRepository;
import com.maybank.integratorapp.data.service.LogInterfaceProcessService;
import com.maybank.integratorapp.data.service.MsCurrencyService;
import com.maybank.integratorapp.model.mq.customersearch.request.*;
import com.maybank.integratorapp.model.mq.customersearch.response.*;
import com.maybank.integratorapp.model.restv2.AccountList.request.*;
import com.maybank.integratorapp.model.restv2.AccountList.request.MsgWraper;
import com.maybank.integratorapp.model.restv2.CustomerInformation.request.*;
import com.maybank.integratorapp.util.MQUtil;
import jakarta.jms.JMSException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Component
public class CustomerSearchMessageProcessor {

    @Autowired
    LogInterfaceProcessService logger;
    @Autowired
    MsParameterRepository parameterRepository;

    @Autowired
    MsCompanyDataRepository companyDataRepository;

    @Autowired
    MsCurrencyService msCurrencyService;

    ServiceResponse response = new ServiceResponse();

    private final String ProcessName = "CustomerSearchProcess";


    public String processMessage(String message,Long loggerId) {
        String responseXml = "";
        logger.SetLogParent(loggerId);

        try {
            // step 1. Parse request message
            // step 2. Set initial response
            // step 3. Map external request to core system request
            // step 4. Request data from core system
            // step 5. Map core system data to external Response
            // step 6. return response message as string

            // step 1.
            ServiceRequest request = parseRequest(message);


            // request validation (if needed)
            boolean validRequest = true;
            if(request.getCustomerSearchRequest().getCustomerNumber() == null){
                Details detailsResponse = new Details();
                detailsResponse.setError("GCIF Is Empty");
                response.getResponseHeader().setDetails(detailsResponse);
                response.getResponseHeader().setStatus("FAILED");
                validRequest = false;

            }
            String tagCustomer = request.getCustomerSearchRequest().getIncludeCustomers();
            String tagBank = request.getCustomerSearchRequest().getIncludeBanks();

            //check 2 tag ini, hanya 1 yang boleh Y
//            <ns2:IncludeCustomers>Y</ns2:IncludeCustomers>
//            <ns2:IncludeBanks>Y</ns2:IncludeBanks>
            // save 2 tag ini ke db
            if(tagCustomer.equals(tagBank)) {
                Details detailsResponse = new Details();
                detailsResponse.setError("Please Check Only One, Bank=Y or Corporate=Y");
                response.getResponseHeader().setDetails(detailsResponse);
                response.getResponseHeader().setStatus("FAILED");
                validRequest = false;
            }

            if (request != null && validRequest) {

                // step 2.
                setInitialResponseHeader(request);

                // step 3.
                MsgWraper msgRequestAccountList = mapCoreSystemAccountListRequest(request);
                com.maybank.integratorapp.model.restv2.CustomerInformation.request.MsgWraper msgRequestCustomerInformation = mapCoreSystemCustomerInformationRequest(request);

                // step 4.
                String accountList_responseStatus;
                String accountList_responseMessage;
                String accountList_add_responseStatus;
                String accountList_add_responseMessage;
                String customerInfo_responseStatus;
                String customerInfo_responseMessage;
                String customerInfo_add_responseStatus;
                String customerInfo_add_responseMessage;
//                com.maybank.integratorapp.model.restv2.AccountList.response.AccountListResponse accountListResponse= null;
//                com.maybank.integratorapp.model.restv2.CustomerInformation.response.CustomerInfoData customerInfoData = null;
                
                com.maybank.integratorapp.model.restv2.AccountList.response.MsgWraper msgResponse = getAccountListMsgBodyResponse(request,msgRequestAccountList);
                com.maybank.integratorapp.model.restv2.CustomerInformation.response.MsgWraper msgResponse2 = null;
                if(msgResponse.getMsg()!=null){
                    accountList_responseStatus = msgResponse.getMsg().getMsgHeader().getStatusCode();
                    accountList_responseMessage = msgResponse.getMsg().getMsgHeader().getStatusDesc();
                    accountList_add_responseStatus = msgResponse.getMsg().getMsgHeader().getAdditionalStatusCodes().get(0).getHostStatusCode();
                    accountList_add_responseMessage = msgResponse.getMsg().getMsgHeader().getAdditionalStatusCodes().get(0).getHostStatusDesc();
                    logger.Log(ProcessName, "Response ESB Account Info", "ESB-MESSAGE", accountList_responseStatus+"|"+accountList_responseMessage+";"+accountList_add_responseStatus+"|"+accountList_add_responseMessage);

                }


                if(msgResponse.getMsg().getMsgHeader().getStatusCode().equals("0")
                        && msgResponse.getMsg().getMsgBody().getAccountListResponse()!=null){
                    msgResponse2 = getCustomerInfoMsgBodyResponse(request,msgRequestCustomerInformation);
                    if(msgResponse2.getMsg()!=null){
                        customerInfo_responseStatus = msgResponse2.getMsg().getMsgHeader().getStatusCode();
                        customerInfo_responseMessage = msgResponse2.getMsg().getMsgHeader().getStatusDesc();
                        customerInfo_add_responseStatus = msgResponse2.getMsg().getMsgHeader().getAdditionalStatusCodes().get(0).getHostStatusCode();
                        customerInfo_add_responseMessage = msgResponse2.getMsg().getMsgHeader().getAdditionalStatusCodes().get(0).getHostStatusDesc();
                        logger.Log(ProcessName, "Response ESB Customer Info", "ESB-MESSAGE", customerInfo_responseStatus+"|"+customerInfo_responseMessage+";"+customerInfo_add_responseStatus+"|"+customerInfo_add_responseMessage);

                    }

                }

                // step 5.
                mapExternalResponse(request, msgResponse,msgResponse2);

            }

            // step 6.
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            responseXml = xmlMapper.writeValueAsString(response);

        } catch (Exception e) {
            logger.Log(ProcessName, "Error Processing Message", "ERROR-PROCESS-MESSAGE", e.getMessage());

        }
        return responseXml;
    }

    private void handleExceptionResponse(String message) {
        response.getResponseHeader().setStatus("Error");
        response.getResponseHeader().getDetails().setError(message);
    }

    // step 1. Parse request message
    private ServiceRequest parseRequest(String message) throws JMSException {
        XmlMapper xmlMapper = new XmlMapper();
        ServiceRequest request;
        try {
            request = xmlMapper.readValue(message, ServiceRequest.class);
        } catch (JsonProcessingException e) {
            logger.Log(ProcessName, "Error Json Processing", "ERROR-JSON-PROCESS", e.getMessage());
            handleExceptionResponse(e.getMessage());
            request = null;
        }
        return request;
    }

    // step 2. Set initial response
    private void setInitialResponseHeader(ServiceRequest request) {
        response.getResponseHeader().setCorrelationID(request.getRequestHeader().getCorrelationID());
        response.getResponseHeader().setService(request.getRequestHeader().getService());
        response.getResponseHeader().setOperation(request.getRequestHeader().getOperation());
        response.getResponseHeader().setSourceSystem(request.getRequestHeader().getTargetSystem());
        response.getResponseHeader().setTargetSystem(request.getRequestHeader().getSourceSystem());
        response.getResponseHeader().setStatus("SUCCEEDED");
    }

    // step 3. Map external request to core system request
    public MsgWraper mapCoreSystemAccountListRequest(ServiceRequest externalRequest) {
        // Buat request CustomerInformation
        MsgWraper req = new MsgWraper();

        String gcif = externalRequest.getCustomerSearchRequest().getCustomerNumber();

        req.getMsg().getMsgHeader().setMsgID("FTI_"+MQUtil.generateRandomString(4));
        req.getMsg().getMsgHeader().setVer("01");
        req.getMsg().getMsgHeader().setSvcID("IDINQACCTLST001");
        req.getMsg().getMsgHeader().setTxnCode("AccountListDCIF");
        req.getMsg().getMsgHeader().setEnv("S");

        req.getMsg().getMsgBody().setGcifNo(gcif);
        req.getMsg().getMsgBody().setRequestDataCategory("2");

        return req;
    }

    public com.maybank.integratorapp.model.restv2.CustomerInformation.request.MsgWraper mapCoreSystemCustomerInformationRequest(ServiceRequest externalRequest) {
        // Buat request CustomerInformation
        com.maybank.integratorapp.model.restv2.CustomerInformation.request.MsgWraper req = new com.maybank.integratorapp.model.restv2.CustomerInformation.request.MsgWraper();

        String gcif = externalRequest.getCustomerSearchRequest().getCustomerNumber();

        req.getMsg().getMsgHeader().setMsgID("FTI_"+MQUtil.generateRandomString(4));
        req.getMsg().getMsgHeader().setVer("01");
        req.getMsg().getMsgHeader().setSvcID("IDINQCUSTINF001");
        req.getMsg().getMsgHeader().setTxnCode("CustInfo_DCIF");
        req.getMsg().getMsgHeader().setEnv("S");
        req.getMsg().getMsgHeader().setHostID("DCIF");

        req.getMsg().getMsgBody().setGcifNo(gcif);

        return req;
    }

    // step 4. Request data from core system
    public com.maybank.integratorapp.model.restv2.AccountList.response.MsgWraper getAccountListMsgBodyResponse(ServiceRequest request, MsgWraper req) {
        com.maybank.integratorapp.model.restv2.AccountList.response.MsgWraper res = new com.maybank.integratorapp.model.restv2.AccountList.response.MsgWraper();
        String api = parameterRepository.findValueByPrmKey("AccountListRequestV2");

        try {
            MsgWraper wraperRequest = new MsgWraper();

            wraperRequest = req;

            // Konversi request ke JSON
            ObjectMapper objectMapper = new ObjectMapper();

            // how to not double json
            objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

            // String Reqjson = objectMapper.writeValueAsString(body);
            String json = objectMapper.writeValueAsString(wraperRequest);
            logger.Log(ProcessName, "Hit ESB Message", "ESB-MESSAGE", json);
            // Kirim request ke API eksternal
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(api);
                httpPost.setHeader("Content-Type", "application/json");
                httpPost.setEntity(new StringEntity(json, "UTF-8"));

                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    String responseString = EntityUtils.toString(response.getEntity());
                    logger.Log(ProcessName, "Response ESB Message", "ESB-MESSAGE", responseString);
                    res = objectMapper.readValue(responseString, com.maybank.integratorapp.model.restv2.AccountList.response.MsgWraper.class);

                    if(res.getMsg().getMsgHeader().getStatusCode().equals("0")){

                        String gcifNo = request.getCustomerSearchRequest().getCustomerNumber();
                        String cifNo = request.getCustomerSearchRequest().getCustomerMnemonic();
                        String tagBank = request.getCustomerSearchRequest().getIncludeBanks();
                        String tagCustomer = request.getCustomerSearchRequest().getIncludeCustomers();
                        var _el = res.getMsg().getMsgBody().getAccountListResponse().getAccountData().stream().filter(x->x.getCifNo().equals(cifNo)).findFirst().get();
                        String responseCifNo = _el.getCifNo();
                        MsCompanyData companyData = companyDataRepository.findByGcif(gcifNo);
                        if(companyData == null){
                            companyData = new MsCompanyData();
                            companyData.setGcifno(gcifNo);
                            companyData.setCreated_date(new Date());
                        }

                        companyData.setCifno(responseCifNo);
                        companyData.setTagBank(tagBank);
                        companyData.setTagCustomer(tagCustomer);
                        companyData.setAccInfoData(responseString);
                        companyData.setUpdated_date(new Date());

                        companyData = companyDataRepository.save(companyData);

                    }


                } catch (Exception e) {
                    logger.Log(ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());
                }
            } catch (IOException e) {
                logger.Log(ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());
            }
        } catch (IOException e) {
            logger.Log(ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());

        }
        return res;
    }
    public com.maybank.integratorapp.model.restv2.CustomerInformation.response.MsgWraper getCustomerInfoMsgBodyResponse(ServiceRequest request, com.maybank.integratorapp.model.restv2.CustomerInformation.request.MsgWraper req) {
        com.maybank.integratorapp.model.restv2.CustomerInformation.response.MsgWraper res = new com.maybank.integratorapp.model.restv2.CustomerInformation.response.MsgWraper();
        String api = parameterRepository.findValueByPrmKey("CustomerInformationRequestV2");

        try {
            com.maybank.integratorapp.model.restv2.CustomerInformation.request.MsgWraper wraperRequest = new com.maybank.integratorapp.model.restv2.CustomerInformation.request.MsgWraper();

            wraperRequest = req;

            // Konversi request ke JSON
            ObjectMapper objectMapper = new ObjectMapper();

            // how to not double json
            objectMapper.setVisibilityChecker(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                    .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

            // String Reqjson = objectMapper.writeValueAsString(body);
            String json = objectMapper.writeValueAsString(wraperRequest);
            logger.Log(ProcessName, "Hit ESB Message", "ESB-MESSAGE", json);

            // Kirim request ke API eksternal
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(api);
                httpPost.setHeader("Content-Type", "application/json");
                httpPost.setEntity(new StringEntity(json, "UTF-8"));

                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    String responseString = EntityUtils.toString(response.getEntity());
                    logger.Log(ProcessName, "Response ESB Message", "ESB-MESSAGE", responseString);

                    res = objectMapper.readValue(responseString, com.maybank.integratorapp.model.restv2.CustomerInformation.response.MsgWraper.class);
                    String gcifNo = request.getCustomerSearchRequest().getCustomerNumber();
                    String tagBank = request.getCustomerSearchRequest().getIncludeBanks();
                    String tagCustomer = request.getCustomerSearchRequest().getIncludeCustomers();

                    MsCompanyData companyData = companyDataRepository.findByGcif(gcifNo);
                    if(companyData == null){
                        companyData = new MsCompanyData();
                        companyData.setGcifno(gcifNo);
                        companyData.setCreated_date(new Date());
                    }

                    companyData.setCustInfoData(responseString);
                    companyData.setUpdated_date(new Date());
                    companyDataRepository.save(companyData);

                    companyData = companyDataRepository.save(companyData);


                } catch (Exception e) {
                    logger.Log(ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());
                }
            } catch (IOException e) {
                logger.Log(ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());
            }
        } catch (IOException e) {
            logger.Log(ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());

        }
        return res;
    }

    // step 5. Map core system data to external Response
    private void mapExternalResponse(ServiceRequest request,com.maybank.integratorapp.model.restv2.AccountList.response.MsgWraper accountListResponse, com.maybank.integratorapp.model.restv2.CustomerInformation.response.MsgWraper customerInfoResponse) {

        if(accountListResponse.getMsg().getMsgBody().getAccountListResponse()!= null
        && customerInfoResponse!=null){
            String requestGcifNo = request.getCustomerSearchRequest().getCustomerNumber();
            String requestCifNo = request.getCustomerSearchRequest().getCustomerMnemonic();

            String responseGcifNo = customerInfoResponse.getMsg().getMsgBody().getCustomerInfoData().getgCIFNo();
            var _el = accountListResponse.getMsg().getMsgBody().getAccountListResponse().getAccountData().stream().filter(x->x.getCifNo().equals(requestCifNo)).findFirst().get();
            String responseCifNo = _el.getCifNo();
            String fullname = customerInfoResponse.getMsg().getMsgBody().getCustomerInfoData().getFullName();
            String countryOrResidence = customerInfoResponse.getMsg().getMsgBody().getCustomerInfoData().getNationality();
            String address = customerInfoResponse.getMsg().getMsgBody().getCustomerInfoData().getAddressLine1() + " "+
                    customerInfoResponse.getMsg().getMsgBody().getCustomerInfoData().getAddressLine2() + " "+
                    customerInfoResponse.getMsg().getMsgBody().getCustomerInfoData().getAddressLine3() + " "+
                    customerInfoResponse.getMsg().getMsgBody().getCustomerInfoData().getAddressLine4() + " "+
                    customerInfoResponse.getMsg().getMsgBody().getCustomerInfoData().getAddressLine5() + " "+
                    customerInfoResponse.getMsg().getMsgBody().getCustomerInfoData().getAddressLine6() + " "+
                    customerInfoResponse.getMsg().getMsgBody().getCustomerInfoData().getAddressLine7() + " "+
                    customerInfoResponse.getMsg().getMsgBody().getCustomerInfoData().getAddressLine8() + " "+
                    customerInfoResponse.getMsg().getMsgBody().getCustomerInfoData().getAddressLine9() + " "+
                    customerInfoResponse.getMsg().getMsgBody().getCustomerInfoData().getAddressLine10();
            address = address.trim();
            if(address.length()>59)
                address = address.substring(0,59);
//            Details detailsResponse = new Details();
//            detailsResponse.setInfo(res.getMsg().getMsgHeader().getStatusDesc());
            CustomerSearchResult customerSearchResult = new CustomerSearchResult();

            customerSearchResult.setGroup("ADIMAH");
            customerSearchResult.setAccountOfficer("*");
            customerSearchResult.setBlocked("N");
            customerSearchResult.setCustomerMnemonic(responseCifNo);
            customerSearchResult.setCustomerNumber(responseGcifNo);
            customerSearchResult.setFullName(fullname);
            customerSearchResult.setCountryOfResidence(countryOrResidence);
            customerSearchResult.setLocation(address);

            ArrayList<CustomerSearchResult> _result = new ArrayList<>();
            _result.add(customerSearchResult);
            response.setCustomerSearchResponse(new CustomerSearchResponse());
            response.getCustomerSearchResponse().setCustomerSearchResults(new CustomerSearchResults());
            response.getCustomerSearchResponse().getCustomerSearchResults().setCustomerSearchResult(_result);

        }else{
            String accountList_responseStatus;
            String accountList_responseMessage;
            String accountList_add_responseStatus;
            String accountList_add_responseMessage;

            accountList_responseStatus = accountListResponse.getMsg().getMsgHeader().getStatusCode();
            accountList_responseMessage = accountListResponse.getMsg().getMsgHeader().getStatusDesc();
            accountList_add_responseStatus = accountListResponse.getMsg().getMsgHeader().getAdditionalStatusCodes().get(0).getHostStatusCode();
            accountList_add_responseMessage = accountListResponse.getMsg().getMsgHeader().getAdditionalStatusCodes().get(0).getHostStatusDesc();

            Details detailsResponse = new Details();
            detailsResponse.setError("ESB-HOST ERROR : "+accountList_responseMessage+"|"+accountList_add_responseMessage);
            response.getResponseHeader().setDetails(detailsResponse);
            response.getResponseHeader().setStatus("FAILED");
            response.setCustomerSearchResponse(null);
//            response.getCustomerSearchResponse().setCustomerSearchResults(new CustomerSearchResults());
//            ArrayList<CustomerSearchResult> _result = new ArrayList<>();
//            response.getCustomerSearchResponse().getCustomerSearchResults().setCustomerSearchResult(_result);

        }

    }
}



