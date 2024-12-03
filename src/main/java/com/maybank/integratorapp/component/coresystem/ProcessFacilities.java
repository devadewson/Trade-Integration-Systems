package com.maybank.integratorapp.component.coresystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.model.mq.facilities.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.facilities.response.*;
import com.maybank.integratorapp.model.soap.limit.XLBT.request.AdditionalHeader;
import com.maybank.integratorapp.model.soap.limit.XLBT.request.SoapEnvelope;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ProcessFacilities {

    @Autowired
    ProcessCustomerDetail customerDetail;

    public ServiceResponse getFacilities(ServiceRequest serviceRequest){

        String soapUrl = "http://10.230.83.57:65085/services/CMSService";
        String correlationID = serviceRequest.getRequestHeader().getCorrelationID();
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

        SoapEnvelope soapReq = new SoapEnvelope();
//        soapReq.getBody().getxLBT().getChannelHeader().setAdditionalHeader(new AdditionalHeader());
//        soapReq.getBody().getxLBT().getChannelHeader().getAdditionalHeader().setParam("maxpage,20;");
        soapReq.getBody().getxLBT().getChannelHeader().setBranchCode("003");
        soapReq.getBody().getxLBT().getChannelHeader().setChannelID("BT");
        soapReq.getBody().getxLBT().getChannelHeader().setClientSupervisorID("LKE");
        soapReq.getBody().getxLBT().getChannelHeader().setClientUserID("B027950");
        soapReq.getBody().getxLBT().getChannelHeader().setReference("B001395000");
        soapReq.getBody().getxLBT().getChannelHeader().setReversalSequenceNo(correlationID);
        soapReq.getBody().getxLBT().getChannelHeader().setTransactionDate(date);
        soapReq.getBody().getxLBT().getChannelHeader().setTransactionTime(time);

        soapReq.getBody().getxLBT().getcMS_XLBTRequest().setCifno("0004613980");
        soapReq.getBody().getxLBT().getcMS_XLBTRequest().setAid("XLBT");

        XmlMapper mapper = new XmlMapper();

//        mapper.enable(MapperFeature.USE_ANNOTATIONS)
//                .enable(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME)
//                .enable(MapperFeature.AUTO_DETECT_CREATORS)
//                .enable(MapperFeature.AUTO_DETECT_FIELDS)
//                .enable(MapperFeature.USE_STD_BEAN_NAMING);
        mapper.setDefaultUseWrapper(false); // Avoid unnecessary wrapping
        mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

        String xml = null;
        com.maybank.integratorapp.model.soap.
                limit.XLBT.response.SoapEnvelope res = new com.maybank.integratorapp.model.
                soap.limit.XLBT.response.SoapEnvelope();
        try {
            xml = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(soapReq);
//            System.out.println("Before");
//            System.out.println(xml);
//            System.out.println("======================================================================");
//            xml = xml.replace("<soapenv:Envelope xmlns=\"http://schemas.xmlsoap.org/soap/envelope/\">",
//                    "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
//                            "xmlns:cms=\"http://cms.middleware.bankbii.com/\">");
//
//            xml = xml.replace("<wstxns1:XLBT xmlns:wstxns1=\"http://www.bankbii.com/AccountServices/\">",
//                    "<cms:XLBT>");
//
//            xml = xml.replace(" xmlns=\"\"", "");
//            xml = xml.replace("</wstxns1:XLBT>", "</cms:XLBT>");

            System.out.println(xml);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        ServiceResponse serviceResponseMq = new ServiceResponse();
        ResponseHeader responseHeaderMq = new ResponseHeader();
        FacilitiesResponse facilitiesResponseMq = new FacilitiesResponse();
        Details detailsResponseMq = new Details();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(soapUrl);
            httpPost.setHeader("Content-Type", "text/xml");
            httpPost.setEntity(new StringEntity(xml, ContentType.TEXT_XML));
            String _response = "";

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == 200){
                    // Handle response if needed
                    var _res = response.getEntity();
                    var _resStream = _res.getContent();

                    var outputResponse = new String(_resStream.readAllBytes(), StandardCharsets.UTF_8);

                    _response = outputResponse;
                    res = mapper.readValue(_response, com.maybank.integratorapp.model.soap.
                            limit.XLBT.response.SoapEnvelope.class);

                    String asd = mapper.writeValueAsString(res);
                    System.out.println("===========================asd=================================");
                    System.out.println(asd);
                    System.out.println("============================================================\n");

                    responseHeaderMq.setCorrelationID(serviceRequest.getRequestHeader().getCorrelationID());
                    responseHeaderMq.setService("LIMIT");
                    responseHeaderMq.setOperation("FACILITIES");
                    responseHeaderMq.setStatus("SUCCEEDED");

                    detailsResponseMq.setInfo(String.valueOf(response.getStatusLine().getStatusCode()));
                    responseHeaderMq.setDetails(detailsResponseMq);

                    int total = res.getBody().getXlbtResponse().getCmsXlbtResponse()
                            .getLoanAccounts().size();
                    List<FacilityDetails> facilityDetailsList = new ArrayList<>(total);
                    for (int i = 0; i < total; i++) {
                        FacilityDetails details = new FacilityDetails();

                        details.setDescription(res.getBody().getXlbtResponse().getCmsXlbtResponse().
                                getLoanAccounts().get(i).getDescription());
                        details.setStatus(res.getBody().getXlbtResponse().getCmsXlbtResponse().
                                getLoanAccounts().get(i).getStatus());
                        details.setCurrency(res.getBody().getXlbtResponse().getCmsXlbtResponse().
                                getLoanAccounts().get(i).getLoancurrencycode());
                        details.setExtraDataKey(res.getBody().getXlbtResponse().getCmsXlbtResponse().
                                getLoanAccounts().get(i).getKey());
                        details.setStartDate(res.getBody().getXlbtResponse().getCmsXlbtResponse().
                                getLoanAccounts().get(i).getNotedate());
                        details.setExpiryDate(res.getBody().getXlbtResponse().getCmsXlbtResponse().
                                getLoanAccounts().get(i).getMaturitydate());

                        facilityDetailsList.add(details);
                    }
                    FacilityDetailss facilityDetailssHead = new FacilityDetailss();
                    facilityDetailssHead.setFacilityDetails(facilityDetailsList);

                    FacilityResponseExtraDetails responseExtraDetails = new FacilityResponseExtraDetails();
                    responseExtraDetails.setExtraDataKey("ExtraDataKey");
                    responseExtraDetails.setFieldName("FieldName");
                    responseExtraDetails.setFieldValue("FieldValue");

                    FacilityResponseExtraDetailss responseExtraDetailssHead = new FacilityResponseExtraDetailss();
                    responseExtraDetailssHead.setFacilityResponseExtraDetails(responseExtraDetails);

                    facilitiesResponseMq.setFacilityDetailss(facilityDetailssHead);
                    facilitiesResponseMq.setFacilityResponseExtraDetailss(responseExtraDetailssHead);

                    serviceResponseMq.setResponseHeader(responseHeaderMq);
                    serviceResponseMq.setFacilitiesResponse(facilitiesResponseMq);
                }

                XmlMapper xmlMapper = new XmlMapper();
                String responseXml = xmlMapper.writeValueAsString(serviceResponseMq);
                System.out.println("===========================responseXml=================================");
                System.out.println(responseXml);
                System.out.println("============================================================\n");

            } catch (ClientProtocolException e) {
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return serviceResponseMq;
    }

}
