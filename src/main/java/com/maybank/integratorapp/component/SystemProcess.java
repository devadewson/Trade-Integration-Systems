package com.maybank.integratorapp.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.maybank.integratorapp.component.AccountWS;
import com.maybank.integratorapp.data.repository.*;
import com.maybank.integratorapp.model.mq.accountinquiry.response.AvailBalResponse;
import com.maybank.integratorapp.model.mq.batchposting.request.ServiceRequestChild;
import com.maybank.integratorapp.model.mq.customersearch.response.CustomerSearchResult;
import com.maybank.integratorapp.model.mq.fxratefcc.response.ExchangeRateRecord;
import com.maybank.integratorapp.model.mq.fxratefcc.response.ExchangeRateRecords;
import com.maybank.integratorapp.model.rest.fxrate.request.FxRateRequest;
import com.maybank.integratorapp.model.rest.fxrate.response.FxRateResponse;
import com.maybank.integratorapp.model.soap.ChannelHeaderType;
import com.maybank.integratorapp.model.soap.accountinformation.response.AccountInformationResponse;
//import com.maybank.integratorapp.model.soap.accountinquiry.request.SoapEnvelope;
import com.maybank.integratorapp.model.soap.accountinquiry.response.AccountInquiryResponse;
import com.maybank.integratorapp.model.soap.accountinquiry.response.SoapEnvelope;
import com.maybank.integratorapp.util.MQUtil;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


//@ContextConfiguration(classes = CountryClientConfig.class, loader = AnnotationConfigContextLoader.class)
//@Service
//@Component
public class SystemProcess {
//    @Autowired
//    private AccountWS ws;

    @Autowired
    private MsTBRFieldRepository msTBRFieldRepository;
    @Autowired
    private MsTBRRepository msTBRRepository;
    @Autowired
    private MsTBRMappingRepository msTBRMappingRepository;
    @Autowired
    private MsAccountTypeRepository msAccountTypeRepository;


    public int doSomething(){
        return 1000000;
    }

    public String getAccountInformation(String accountNumber){
        String accBalance = "0";

        ChannelHeaderType headerType = new ChannelHeaderType();
        headerType.setMessageID("TestingIDFromNewBankTradeIntegrator");
        headerType.setBranchCode("270");
        headerType.setChannelID("Payroll");
        headerType.setClientSupervisorID("AA");

        AccountWS ws = new AccountWS();
//        ws.init();
        ws.setCh(headerType);

        AccountInformationResponse outputResponse = ws.callAccountInformation(accountNumber,"016");
        accBalance = outputResponse.getResponseCode();
//        int posParse = outputResponse.indexOf("<Balance>") + "<Balance>".length();
//        int posEndParse = outputResponse.indexOf("</Balance>");

//        accBalance = outputResponse.substring(posParse,posEndParse).replace("+","");
//        accBalance = Double.parseDouble(response);
//        System.out.println(response);


        return accBalance;
    }

    public void doPosting(List<ServiceRequestChild> listPosting){




    }


    public List<CustomerSearchResult> getCustomerSearch(String customerMnemonic) {

        List<CustomerSearchResult> searchResults = new ArrayList<>();

        try {
            ArrayList<CustomerSearchResult> dataDummy = new ArrayList<>();
            //dummy data
            for (int i = 0; i < 10; i++) {  // Generate 10 dummy objects
                CustomerSearchResult searchResult = new CustomerSearchResult();
                searchResult.setFullName(MQUtil.generateRandomString(25));
                searchResult.setCustomerNumber(MQUtil.generateRandomString(10));

                if (i == 0 && i == 2) {
                    searchResult.setCustomerMnemonic(customerMnemonic);
                } else {
                    searchResult.setCustomerMnemonic(MQUtil.generateRandomString(10));
                }
                searchResult.setLocation(MQUtil.generateRandomString(15));
                searchResult.setCountryOfResidence(MQUtil.generateRandomString(12));
                searchResult.setAccountOfficer(MQUtil.generateRandomString(25));
                dataDummy.add(searchResult);
            }

            if(dataDummy.stream().anyMatch(x->x.getCustomerMnemonic().equals(customerMnemonic))){

                searchResults.addAll(
                        dataDummy.stream()
                                .filter(x->x.getCustomerMnemonic().equals(customerMnemonic))
                                .collect(Collectors.toList())
                );
//                dataDummy.stream()
//                        .filter(x->x.getCustomerMnemonic().equals(customerMnemonic))
//                        .findFirst()
//                        .ifPresent(searchResults::add);
            }

        }catch (Exception ex){
            throw new RuntimeException(ex);
        }

        return searchResults;
    }
}
