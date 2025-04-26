package com.maybank.integratorapp.component.system.messageprocessor;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.maybank.integratorapp.data.repository.MsParameterRepository;
import com.maybank.integratorapp.data.service.LogInterfaceProcessService;
import com.maybank.integratorapp.data.service.MsCurrencyService;
import com.maybank.integratorapp.model.mq.accountinquiry.request.AvailBalRequest;
import com.maybank.integratorapp.model.mq.accountinquiry.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.accountinquiry.response.AvailBalResponse;
import com.maybank.integratorapp.model.mq.accountinquiry.response.Details;
import com.maybank.integratorapp.model.mq.accountinquiry.response.ResponseHeader;
import com.maybank.integratorapp.model.mq.accountinquiry.response.ServiceResponse;
import com.maybank.integratorapp.model.mq.customersearch.response.CustomerSearchResult;
import com.maybank.integratorapp.model.rest.AccountInquiry.response.AccountInquiryResponse;
import com.maybank.integratorapp.model.rest.CustomerSearchh.request.ChannelHeader;
import com.maybank.integratorapp.model.rest.CustomerSearchh.request.CustomerInformation;
import com.maybank.integratorapp.model.rest.CustomerSearchh.request.CustomerInformationWraper;
import com.maybank.integratorapp.model.rest.CustomerSearchh.response.CustomerInformationResponseWraper;
import com.maybank.integratorapp.model.restv2.AccountInquiry.request.Msg;
import com.maybank.integratorapp.model.restv2.AccountInquiry.request.MsgBody;
import com.maybank.integratorapp.model.restv2.AccountInquiry.request.MsgWraper;
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
import java.math.BigInteger;
import java.util.logging.Logger;

@Component
public class AccountInquiryMessageProcessor {

    @Autowired
    LogInterfaceProcessService logger;
    @Autowired
    MsParameterRepository parameterRepository;

    @Autowired
    MsCurrencyService msCurrencyService;

    ServiceResponse response = new ServiceResponse();

    private final String ProcessName = "AccountInquiryProcess";

    private long LoggerId;

    public String processMessage(String message,long loggerId) {
        String responseXml = "";
        this.LoggerId = loggerId;
//        logger.SetLogParent(loggerId);

        try {
            // step 1. Parse request message
            // step 2. Set initial response
            // step 3. Map external request to core system request
            // step 4. Request data from core system
            // step 5. Map core system data to external Response
            // step 6. return response message as string

            // step 1.
            ServiceRequest request = parseRequest(message);

            if (request != null) {

                // step 2.
                setInitialResponseHeader(request);

                // step 3.
                MsgWraper msgRequest = mapCoreSystemRequest(request);

                // step 4.
                com.maybank.integratorapp.model.restv2.AccountInquiry.response.MsgWraper msgResponse = getMsgBodyResponse(msgRequest);

                // step 5.
                mapExternalResponse(msgResponse,msgRequest);

            }

            // step 6.
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            responseXml = xmlMapper.writeValueAsString(response);

        } catch (Exception e) {
            logger.Log(LoggerId,ProcessName, "Error Processing Message", "ERROR-PROCESS-MESSAGE", e.getMessage());

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
            logger.Log(LoggerId,ProcessName, "Error Json Processing", "ERROR-JSON-PROCESS", e.getMessage());
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
    public MsgWraper mapCoreSystemRequest(ServiceRequest externalRequest) {
        // Buat request CustomerInformation
        MsgWraper req = new MsgWraper();

        String accNo = externalRequest.getAvailBALRequest().getBackOfficeAccount();
        // ambil branch dari digit ke-2 sampai 4 dari akun
        String branch = accNo.substring(1, 4);
        String currency = msCurrencyService.findByIsoCode(externalRequest.getAvailBALRequest().getPostingCurrency()).getInternalCode();

        req.getMsg().getMsgHeader().setMsgID("FTI"+ MQUtil.generateRandomString(6));
        req.getMsg().getMsgHeader().setVer("01");
        req.getMsg().getMsgHeader().setSvcID("IDINQACCTINF001");
        req.getMsg().getMsgHeader().setTxnCode("IMSTXLI1");
        req.getMsg().getMsgHeader().setEnv("S");
        req.getMsg().getMsgHeader().setHostID("SYSTEMATIC");

        req.getMsg().getMsgBody().setAccountNo(accNo);
        req.getMsg().getMsgBody().setAccountBranchCode(branch);
        req.getMsg().getMsgBody().setAccountCurrency(currency);

        return req;
    }

    // step 4. Request data from core system
    public com.maybank.integratorapp.model.restv2.AccountInquiry.response.MsgWraper getMsgBodyResponse(MsgWraper req) {
        com.maybank.integratorapp.model.restv2.AccountInquiry.response.MsgWraper res = new com.maybank.integratorapp.model.restv2.AccountInquiry.response.MsgWraper();
        String api = parameterRepository.findValueByPrmKey("AccountInquiryRequestV2");

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
            logger.Log(LoggerId,ProcessName, "Request Message", "ESB-MESSAGE", json);


            // Kirim request ke API eksternal
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(api);
                httpPost.setHeader("Content-Type", "application/json");
                httpPost.setEntity(new StringEntity(json, "UTF-8"));

                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    String responseString = EntityUtils.toString(response.getEntity());
                    logger.Log(LoggerId,ProcessName, "Response Message", "ESB-MESSAGE", responseString);

                    res = objectMapper.readValue(responseString, com.maybank.integratorapp.model.restv2.AccountInquiry.response.MsgWraper.class);


                } catch (Exception e) {
                    logger.Log(LoggerId,ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());
                }
            } catch (IOException e) {
                logger.Log(LoggerId,ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());
            }
        } catch (IOException e) {
            logger.Log(LoggerId,ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());

        }
        return res;
    }

    // step 5. Map core system data to external Response
    private void mapExternalResponse(com.maybank.integratorapp.model.restv2.AccountInquiry.response.MsgWraper res,MsgWraper req) {
        AccountInquiryResponse accountInquiryResponse = new AccountInquiryResponse();

//            Details detailsResponse = new Details();
//            detailsResponse.setInfo(res.getMsg().getMsgHeader().getStatusDesc());

        AvailBalResponse availBalResponse = new AvailBalResponse();

        String balance = res.getMsg().getMsgBody().getAccountInformationResponseData().getAccountData().getcADataRecord().getAvailableBalance();
//        String formattedBalance = balance.substring(1).replace(".", "");
        String formattedBalance = balance.substring(1);
        String holdCode = res.getMsg().getMsgBody().getAccountInformationResponseData().getAccountStatus();
        String cifNo = res.getMsg().getMsgBody().getAccountInformationResponseData().getCifNo();
        String accountName = res.getMsg().getMsgBody().getAccountInformationResponseData().getAccountName();
        if(accountName.length()>75){
            accountName= accountName.substring(0,75);
        }
        String infoMessage = "#CIF:"+cifNo+" NAME:"+accountName;
//        String infoMessage = "";

        formattedBalance = String.format("%015.2f",Double.parseDouble(formattedBalance));

        if (balance.startsWith("+"))
            availBalResponse.setNegative("N");
        else
            availBalResponse.setNegative("Y");
        availBalResponse.setBlocked("N");
        availBalResponse.setApplies("Y");
        availBalResponse.setErrorOrWarning("N");
        availBalResponse.setCheckedInBackOffice("Y");
        availBalResponse.setErrorCode("N");
        availBalResponse.setErrorMessage("HOLDCODE-" + holdCode+infoMessage);
        availBalResponse.setBalance(formattedBalance);

        response.setAvailBalResponse(availBalResponse);
    }
}



