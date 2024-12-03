package com.maybank.integratorapp.component.coresystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.model.soap.XL41.request.SoapEnvelope;
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
public class ProcessReservationReversalXL41 {
        public String getReversalReservation(String noteNumber) {

        String soapUrl = "http://10.230.83.57:65085/services/CMSService";
        String correlationID = "ServiceRequest.getRequestHeader().getCorrelationID();";
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

            SoapEnvelope soapReqXL41 = new SoapEnvelope();
            soapReqXL41.getBody().getXl41().getChannelHeader().setAdditionalHeader("");
            soapReqXL41.getBody().getXl41().getChannelHeader().setBranchCode("003");
            soapReqXL41.getBody().getXl41().getChannelHeader().setChannelID("BT");
            soapReqXL41.getBody().getXl41().getChannelHeader().setClientSupervisorID("LKE");
            soapReqXL41.getBody().getXl41().getChannelHeader().setClientUserID("B027950");
            soapReqXL41.getBody().getXl41().getChannelHeader().setReference("L902795000");
            soapReqXL41.getBody().getXl41().getChannelHeader().setReversalSequenceNo(correlationID);
            soapReqXL41.getBody().getXl41().getChannelHeader().setTransactionDate(date);
            soapReqXL41.getBody().getXl41().getChannelHeader().setTransactionTime(time);

            soapReqXL41.getBody().getXl41().getCmsXl41Request().setCtl2("016");
            soapReqXL41.getBody().getXl41().getCmsXl41Request().setCtl3("003");
            soapReqXL41.getBody().getXl41().getCmsXl41Request().setCust("0004613980");
            soapReqXL41.getBody().getXl41().getCmsXl41Request().setDraw("002");
            soapReqXL41.getBody().getXl41().getCmsXl41Request().setFlag("P");
            soapReqXL41.getBody().getXl41().getCmsXl41Request().setNote("22334401");
            soapReqXL41.getBody().getXl41().getCmsXl41Request().setPart("99");

            XmlMapper mapperXL41 = new XmlMapper();

            // Avoid unnecessary wrapping
            mapperXL41.setDefaultUseWrapper(false);
            mapperXL41.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

            String xmlXL41 = null;
            try {
                xmlXL41 = mapperXL41.writeValueAsString(soapReqXL41);
                System.out.println(xmlXL41);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            com.maybank.integratorapp.model.soap.XL41.response.SoapEnvelope cmsResponseXL41 = null;
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(soapUrl);
                httpPost.setHeader("Content-Type", "text/xml");
                httpPost.setEntity(new StringEntity(xmlXL41, ContentType.TEXT_XML));
                String _responseXL41 = "";

                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                    // Handle response if needed
                    var _resXL41 = response.getEntity();
                    var _resStreamXL41 = _resXL41.getContent();
                    var outputResponseXL41 = new String(_resStreamXL41.readAllBytes(), StandardCharsets.UTF_8);
                    _responseXL41 = outputResponseXL41;
                    cmsResponseXL41 = mapperXL41.readValue(_responseXL41, com.maybank.integratorapp.model.soap.XL41.response.SoapEnvelope.class);

                    String xmlResponseXL31 = mapperXL41.writeValueAsString(cmsResponseXL41);
                    System.out.println(xmlResponseXL31);

                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            // Return objek CMS_XL31Response
            return "sukses";
        }


}
