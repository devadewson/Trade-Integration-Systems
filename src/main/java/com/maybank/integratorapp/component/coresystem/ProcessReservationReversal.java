package com.maybank.integratorapp.component.coresystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.model.soap.XL31.request.SoapEnvelope;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
@Component
public class ProcessReservationReversal {
            public String getReversalReservation(String noteNumber) {

            String soapUrl = "http://10.230.83.57:65085/services/CMSService";
            String correlationID = "ServiceRequest.getRequestHeader().getCorrelationID();";
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
            SoapEnvelope soapReqXL31 = new SoapEnvelope();

                soapReqXL31.getBody().getXl31().getChannelHeader().setAdditionalHeader("");
                soapReqXL31.getBody().getXl31().getChannelHeader().setBranchCode("003");
                soapReqXL31.getBody().getXl31().getChannelHeader().setChannelID("BT");
                soapReqXL31.getBody().getXl31().getChannelHeader().setClientSupervisorID("LKE");
                soapReqXL31.getBody().getXl31().getChannelHeader().setClientUserID("B027950");
                soapReqXL31.getBody().getXl31().getChannelHeader().setReference("L902795000");
                soapReqXL31.getBody().getXl31().getChannelHeader().setReversalSequenceNo(correlationID);
                soapReqXL31.getBody().getXl31().getChannelHeader().setTransactionDate(date);
                soapReqXL31.getBody().getXl31().getChannelHeader().setTransactionTime(time);

                soapReqXL31.getBody().getXl31().getCmsXl31Request().setAmount("0000100100100.00");
                soapReqXL31.getBody().getXl31().getCmsXl31Request().setBatch("00301");
                soapReqXL31.getBody().getXl31().getCmsXl31Request().setBd("");
                soapReqXL31.getBody().getXl31().getCmsXl31Request().setCurrency("IDR");
                soapReqXL31.getBody().getXl31().getCmsXl31Request().setDepartement("003");
                soapReqXL31.getBody().getXl31().getCmsXl31Request().setDescription("ISS-L902795");
                soapReqXL31.getBody().getXl31().getCmsXl31Request().setNotenumber(noteNumber);
                soapReqXL31.getBody().getXl31().getCmsXl31Request().setQual("0");
                soapReqXL31.getBody().getXl31().getCmsXl31Request().setTran("67");
                soapReqXL31.getBody().getXl31().getCmsXl31Request().setTransactiondate("281124");

                XmlMapper mapperXL31 = new XmlMapper();

                // Avoid unnecessary wrapping
                mapperXL31.setDefaultUseWrapper(false);
                mapperXL31.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

                String xmlXL31 = null;
                try {
                    xmlXL31 = mapperXL31.writeValueAsString(soapReqXL31);
                    System.out.println(xmlXL31);

                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }

                com.maybank.integratorapp.model.soap.XL31.response.SoapEnvelope cmsResponseXL31 = null;
                try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                    HttpPost httpPost = new HttpPost(soapUrl);
                    httpPost.setHeader("Content-Type", "text/xml");
                    httpPost.setEntity(new StringEntity(xmlXL31, ContentType.TEXT_XML));
                    String _responseXL31 = "";

                    try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                        // Handle response if needed
                        var _resXL31 = response.getEntity();
                        var _resStreamXL31 = _resXL31.getContent();
                        var outputResponseXL31 = new String(_resStreamXL31.readAllBytes(), StandardCharsets.UTF_8);
                        _responseXL31 = outputResponseXL31;
                        cmsResponseXL31 = mapperXL31.readValue(_responseXL31, com.maybank.integratorapp.model.soap.XL31.response.SoapEnvelope.class);

                        String xmlResponseXL31 = mapperXL31.writeValueAsString(cmsResponseXL31);
                        System.out.println(xmlResponseXL31);

                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
                // Return objek CMS_XL31Response
                return "sukses";
            }
    }
