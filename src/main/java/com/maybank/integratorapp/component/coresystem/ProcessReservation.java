package com.maybank.integratorapp.component.coresystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.model.soap.XL01.request.SoapEnvelope;
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
import java.util.List;

import com.maybank.integratorapp.model.soap.XL01.responseComplete.additionalData;
@Component
public class ProcessReservation {
        public String getReversal() {
            String soapUrl = "http://10.230.83.57:65085/services/CMSService";
            SoapEnvelope soapReqXL01 = new SoapEnvelope();
            String correlationID = "ServiceRequest.getRequestHeader().getCorrelationID();";
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

            // Request To CLS XL01DRAW001
            soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setAdditionalHeader("");
            soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setBranchCode("003");
            soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setChannelID("BT");
            soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setClientSupervisorID("LKE");
            soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setClientUserID("B027950");
            soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setReference("L902795000");
            soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setReversalSequenceNo(correlationID);
            soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setTransactionDate(date);
            soapReqXL01.getBody().getXl01Draw001().getChannelHeader().setTransactionTime(time);

            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setAccrMeth("");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setAcct("0004613980.22334401.002.99");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setAdj1Pct("");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setAdj1Type("");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setAdj2Pct("");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setAdj2Type("");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setAppl("XL");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setAvlPeriode("");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setBatch("00301");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setBranch("003");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCeilingRate("");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setChgmeth("0");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCifNo("0004613980");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCommitmentCode("3");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCommitmentType("1");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCtl2("016");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCtl3("003");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCtl4("0004");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setCurrency("IDR");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setDatiIi("");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setDays("");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setDepartement("003");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setFloorCeilingUse("");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setFloorRate("");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setGoldeb("");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setIntstart("010923");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setMatdate("011223");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setNotedate("010923");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setPrinamt("0000000000000.00");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setProductType("541");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setRate("000.000010");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setRelCd("01");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setStatus("A");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setUseAcct1("2");
            soapReqXL01.getBody().getXl01Draw001().getCmsXl01Draw001Request().setUserCode("LKE");

            XmlMapper mapper = new XmlMapper();

            // Avoid unnecessary wrapping
            mapper.setDefaultUseWrapper(false);
            mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

            String xmlXL01 = null;
            try {
                xmlXL01 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(soapReqXL01);

                System.out.println(xmlXL01);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            com.maybank.integratorapp.model.soap.XL01.responseComplete.SoapEnvelope cmsResponseXL01 = null;

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(soapUrl);
                httpPost.setHeader("Content-Type", "text/xml");
                httpPost.setEntity(new StringEntity(xmlXL01, ContentType.TEXT_XML));
                String _response = "";

                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                    // Handle response if needed
                    var _res = response.getEntity();
                    var _resStream = _res.getContent();
                    var outputResponse = new String(_resStream.readAllBytes(), StandardCharsets.UTF_8);
                    _response = outputResponse;
                    cmsResponseXL01 = mapper.readValue(_response,com.maybank.integratorapp.model.soap.XL01.responseComplete.SoapEnvelope.class);

                    String xmlResponseXL01 = mapper.writeValueAsString(cmsResponseXL01);
                    System.out.println(xmlResponseXL01);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            //Get Note Number From Response XL01
            String noteNumber = "" ;
            if (cmsResponseXL01 != null && cmsResponseXL01.getBody().getXl01Draw001Response()
                    .getCmsXL01Draw001Response().getResponseDetail().getAdditionalData()!= null) {
                List<additionalData> additionalDataList = cmsResponseXL01.getBody().getXl01Draw001Response()
                        .getCmsXL01Draw001Response().getResponseDetail().getAdditionalData();
                if (additionalDataList != null) {
                    for (additionalData data : additionalDataList) {
                        if ("noteNumber".equals(data.getParam())) {
                            // Return nilai noteNumber
                            noteNumber = data.getValue();
                        }
                    }
                }
            }

            //Request To XL31
            com.maybank.integratorapp.model.soap.XL31.request.SoapEnvelope soapReqXL31 =
                    new com.maybank.integratorapp.model.soap.XL31.request.SoapEnvelope();

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
            soapReqXL31.getBody().getXl31().getCmsXl31Request().setTran("62");
            soapReqXL31.getBody().getXl31().getCmsXl31Request().setTransactiondate("281124");

            XmlMapper mapperXL31 = new XmlMapper();

            // Avoid unnecessary wrapping
            mapper.setDefaultUseWrapper(false);
            mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

            String xmlXL31 = null;
            try {
                xmlXL31 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(soapReqXL31);
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
                    cmsResponseXL31 = mapper.readValue(_responseXL31, com.maybank.integratorapp.model.soap.XL31.response.SoapEnvelope.class);

                    String xmlResponseXL31 = mapper.writeValueAsString(cmsResponseXL31);
                    System.out.println(xmlResponseXL31);

                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            // Return objek CMS_XL31Response
            return "sukses";
        }
    }

