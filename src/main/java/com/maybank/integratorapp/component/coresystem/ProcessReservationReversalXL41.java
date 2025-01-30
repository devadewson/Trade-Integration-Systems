package com.maybank.integratorapp.component.coresystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.data.entity.MsCurrency;
import com.maybank.integratorapp.data.entity.MsFacilityUtilize;
import com.maybank.integratorapp.data.repository.MsCurrencyRepository;
import com.maybank.integratorapp.data.repository.MsFacilityUtilizeRepository;
import com.maybank.integratorapp.model.soap.XL41.request.SoapEnvelope;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private MsCurrencyRepository msCurrencyRepository;
    @Autowired
    private MsFacilityUtilizeRepository msFacilityUtilizeRepository;
    private String[] splitKey(String key) {
        String bank = key.substring(0, 2);
        String currency = key.substring(2, 5);
        String branchCode = key.substring(5, 8);
        String cif = key.substring(8, 18);
        String note = key.substring(18, 26);
        String draw = key.substring(26, 29);
        String seq = key.substring(29, 31);

        return new String[]{bank, currency, branchCode, cif, note, draw, seq};
    }
    public String getReversalReservation(String reservationIdentitifer,String referenceId,String facilityIdentifier, String transactionDate) {

        String[] splittedKey = splitKey(reservationIdentitifer);
        String limitCurrency = splittedKey[1];
        String limitBranch = splittedKey[2];
        String limitCif = splittedKey[3];
        String limitNoteKey = splittedKey[4];
        String limitDraw = splittedKey[5];

        MsFacilityUtilize msFacilityUtilize = msFacilityUtilizeRepository.findByKeyLoanAcc(reservationIdentitifer);

        String exposureAmount = msFacilityUtilize.getPrincipalBalance();
        List<MsCurrency> currencies = (List<MsCurrency>) msCurrencyRepository.findAll();
        String ISOcurrency = currencies.stream().filter(x->x.getInternalCode().equals(limitCurrency)).findFirst().get().getIsoCode();


        String soapUrl = "http://10.230.83.57:65085/services/CMSService";
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

        SoapEnvelope soapReqXL41 = new SoapEnvelope();
        soapReqXL41.getBody().getXl41().getChannelHeader().setAdditionalHeader("");
        soapReqXL41.getBody().getXl41().getChannelHeader().setBranchCode("003");
        soapReqXL41.getBody().getXl41().getChannelHeader().setChannelID("BT");
        soapReqXL41.getBody().getXl41().getChannelHeader().setClientSupervisorID("7766");
        soapReqXL41.getBody().getXl41().getChannelHeader().setClientUserID("7755");
        soapReqXL41.getBody().getXl41().getChannelHeader().setReference(referenceId);
//        soapReqXL41.getBody().getXl41().getChannelHeader().setReversalSequenceNo(correlationID);
        soapReqXL41.getBody().getXl41().getChannelHeader().setTransactionDate(date);
        soapReqXL41.getBody().getXl41().getChannelHeader().setTransactionTime(time);

        soapReqXL41.getBody().getXl41().getCmsXl41Request().setCtl2(limitCurrency);
        soapReqXL41.getBody().getXl41().getCmsXl41Request().setCtl3(limitBranch);
        soapReqXL41.getBody().getXl41().getCmsXl41Request().setCust(limitCif);
        soapReqXL41.getBody().getXl41().getCmsXl41Request().setDraw(limitDraw);
        soapReqXL41.getBody().getXl41().getCmsXl41Request().setFlag("D");
        soapReqXL41.getBody().getXl41().getCmsXl41Request().setNote(limitNoteKey);
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
                String finalResponseCode = cmsResponseXL41.getBody().getXl41Response().getCmsXl41Response().getResponsecode();

                String xmlResponseXL41 = mapperXL41.writeValueAsString(cmsResponseXL41);
                System.out.println(xmlResponseXL41);

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        // Return objek CMS_XL31Response
        return "sukses";
    }
}
