package com.maybank.integratorapp.component.coresystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.maybank.integratorapp.component.AccountWS;
import com.maybank.integratorapp.model.mq.accountinquiry.response.AvailBalResponse;
import com.maybank.integratorapp.model.soap.ChannelHeaderType;
import com.maybank.integratorapp.model.soap.accountinquiry.response.AccountInquiryResponse;
import com.maybank.integratorapp.model.soap.accountinquiry.response.SoapEnvelope;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class ProcessAccountBalance {

    public String getAccountBalance(String accountNumber){
        String accBalance = "0";

        AvailBalResponse _res = new AvailBalResponse();

        ChannelHeaderType headerType = new ChannelHeaderType();
        headerType.setMessageID("TestingIDFromNewBankTradeIntegrator");
        headerType.setBranchCode("270");
        headerType.setChannelID("Payroll");
        headerType.setClientSupervisorID("AA");

        AccountWS ws = new AccountWS();
//        ws.init();
        ws.setCh(headerType);

        AccountInquiryResponse outputResponse = ws.callAccountInquiry(accountNumber,"016");
        accBalance = outputResponse.getResponseData().getBalance();

        _res.setBalance(accBalance);
        _res.setNegative(accBalance.startsWith("-")?"Y":"N");

//        int posParse = outputResponse.indexOf("<Balance>") + "<Balance>".length();
//        int posEndParse = outputResponse.indexOf("</Balance>");

//        accBalance = outputResponse.substring(posParse,posEndParse).replace("+","");
//        accBalance = Double.parseDouble(response);
//        System.out.println(response);


        return accBalance;
//        return _res;
    }


    public String getAccountBalanceNew(String accountNumber){
        String balance = "";

        String url = "http://10.235.66.95:7800/AccountServices";
        com.maybank.integratorapp.model.soap.accountinquiry.request.SoapEnvelope newReq = new com.maybank.integratorapp.model.soap.accountinquiry.request.SoapEnvelope();
        newReq.getBody().getAccountInquiry().getChannelHeader().setChannelID("Payroll");
        newReq.getBody().getAccountInquiry().getChannelHeader().setBranchCode("270");
        newReq.getBody().getAccountInquiry().getChannelHeader().setClientSupervisorID("AA");
        newReq.getBody().getAccountInquiry().getChannelHeader().setMessageID("TESTINGFROMMQ");
        newReq.getBody().getAccountInquiry().getAccountInquiryRequest().setAccountNo(accountNumber);
        newReq.getBody().getAccountInquiry().getAccountInquiryRequest().setAccountCurrency("016");
        newReq.getBody().getAccountInquiry().getAccountInquiryRequest().setAccountBranchCode("003");

        XmlMapper mapper = new XmlMapper();
        ObjectMapper objectMapper = new ObjectMapper();

        mapper.enable(MapperFeature.USE_ANNOTATIONS)
                .enable(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME)
//                .enable(DeserializationFeature.UNWRAP_ROOT_VALUE)
                .enable(MapperFeature.AUTO_DETECT_CREATORS)
                .enable(MapperFeature.AUTO_DETECT_FIELDS);
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        mapper.enable(Dese);
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//        String xmlString = objectMapper.writeValueAsString(envelope);

        String xml = null;
        try {
            xml = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(newReq);
//            xml = mapper.writeValueAsString(newReq);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "text/xml");
            httpPost.setEntity(new StringEntity(xml, ContentType.TEXT_XML));
            String _response = "";

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                // Handle response if needed
                var _res = response.getEntity();
                var _resStream = _res.getContent();
                var outputResponse = new String(_resStream.readAllBytes(), StandardCharsets.UTF_8);
                _response = outputResponse;
                SoapEnvelope res = mapper.readValue(_response, SoapEnvelope.class);
                balance = res.getBody().getAccountInquiryResponseData().getResponseData().getBalance();
            } catch (ClientProtocolException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return balance;
    }
}
