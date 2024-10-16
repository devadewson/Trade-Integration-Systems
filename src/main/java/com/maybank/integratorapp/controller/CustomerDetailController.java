package com.maybank.integratorapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.component.coresystem.ProcessCustomerDetail;
import com.maybank.integratorapp.model.mq.customerdetail.response.*;
import com.maybank.integratorapp.model.rest.AccountList.response.AccountListResponse;
import com.maybank.integratorapp.model.rest.CustomerDetail.response.CustomerInformationResponse;
import com.maybank.integratorapp.service.MsQueueConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerDetailController {
    @Autowired
    MsQueueConfigService queueConfigService;

    @PostMapping("/CustomerDetail")
    public ResponseEntity<String> GetCustomerDetail(){
        ProcessCustomerDetail processCustomerDetail = new ProcessCustomerDetail();
        CustomerInformationResponse informationResponse = processCustomerDetail.getCustomerDetail("G000709212");

        ServiceResponse serviceResponse = new ServiceResponse();
        ResponseHeader responseHeader = new ResponseHeader();
        Details detailsResponse = new Details();
        CustomerDetailsResponse customerDetailsResponse = new CustomerDetailsResponse();

        detailsResponse.setInfo(informationResponse.getResponseDetail().getResponseData());
        responseHeader.setService("Customer");
        responseHeader.setOperation("CustomerDetails");

        responseHeader.setStatus(informationResponse.getResponseDetail().getResponseData() + "_" +
                informationResponse.getResponseDetail().getErrorOrigin() +"_"+
                informationResponse.getResponseDetail().getResponseCode());

        customerDetailsResponse.setFullName(informationResponse.getCustomerInformationResponseData().getFullName());
        customerDetailsResponse.setCustomerNumber(informationResponse.getCustomerInformationResponseData().getGCIFNo());

        AddressDetails addressDetails = new AddressDetails();
        AddressDetail detailAddress = new AddressDetail();

        String fullAddress = informationResponse.getCustomerInformationResponseData().getAddressLine1() + " " +
                informationResponse.getCustomerInformationResponseData().getAddressLine2() + " " +
                informationResponse.getCustomerInformationResponseData().getAddressLine3();

        detailAddress.setAddressType(fullAddress);
        addressDetails.setAddressDetail(detailAddress);
        customerDetailsResponse.setAddressDetails(addressDetails);

        serviceResponse.setResponseHeader(responseHeader);
        serviceResponse.setCustomerDetailsResponse(customerDetailsResponse);

        AccountListResponse accountListResponse = processCustomerDetail.getAccListByGcifNo("G000709212");
        String cifNo = accountListResponse.getAccountListResponseData().getAccountData().get(0).getCifNo();
        String taxId = informationResponse.getCustomerInformationResponseData().getNPWP();
        String lineOfBussiness = informationResponse.getCustomerInformationResponseData().getLineOfBusiness();
        String national = informationResponse.getCustomerInformationResponseData().getNationality();
        String zipCode = accountListResponse.getAccountListResponseData().getZipcode();
        String custType = informationResponse.getCustomerInformationResponseData().getCustomerType();


        customerDetailsResponse.setCustomerExtraData(new CustometExtraData());
        customerDetailsResponse.getCustomerExtraData().setCifNumber(cifNo);
        customerDetailsResponse.getCustomerExtraData().setTaxId(taxId);
        customerDetailsResponse.getCustomerExtraData().setLineOfBusiness(lineOfBussiness);

        detailAddress.setZipCode(zipCode);
        customerDetailsResponse.setResidenceCountry(national);
        customerDetailsResponse.setCustomerType(custType);

        XmlMapper xmlMapper = new XmlMapper();
        String responseXml = "";
        try {
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            xmlMapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);
            responseXml = xmlMapper.writeValueAsString(serviceResponse);;
        }
        catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("===========================xmlResponse=================================");
        System.out.println(responseXml);
        System.out.println("============================================================\n");
        return new ResponseEntity<>(responseXml, HttpStatus.OK);
    }
}
