package com.maybank.integratorapp.component.coresystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.data.entity.MsCurrency;
import com.maybank.integratorapp.data.entity.MsFacilityUtilize;
import com.maybank.integratorapp.data.repository.MsCurrencyRepository;
import com.maybank.integratorapp.data.repository.MsFacilityUtilizeRepository;
import com.maybank.integratorapp.data.service.MsParameterService;
import com.maybank.integratorapp.model.soap.XL31.request.SoapEnvelope;
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
public class ProcessReservationReversal {
    @Autowired
    private MsCurrencyRepository msCurrencyRepository;
    @Autowired
    private MsParameterService parameterService;
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


//        String soapUrl = "http://10.230.83.57:65085/services/CMSService";
        String soapUrl = parameterService.findValueByPrmKey("XL41Request");
        String clsChannelId = parameterService.findValueByPrmKey("CLSChannelId");
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());

        SoapEnvelope soapReqXL31 = new SoapEnvelope();

        soapReqXL31.getBody().getXl31().getChannelHeader().setAdditionalHeader("");
        soapReqXL31.getBody().getXl31().getChannelHeader().setBranchCode("003");
        soapReqXL31.getBody().getXl31().getChannelHeader().setChannelID("FTI");
        soapReqXL31.getBody().getXl31().getChannelHeader().setClientSupervisorID("7766");
        soapReqXL31.getBody().getXl31().getChannelHeader().setClientUserID("7755");
        soapReqXL31.getBody().getXl31().getChannelHeader().setReference(referenceId);
//                    soapReqXL31.getBody().getXl31().getChannelHeader().setReversalSequenceNo(correlationID);
        soapReqXL31.getBody().getXl31().getChannelHeader().setTransactionDate(date);
        soapReqXL31.getBody().getXl31().getChannelHeader().setTransactionTime(time);

        soapReqXL31.getBody().getXl31().getCmsXl31Request().setAmount(exposureAmount);
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setBatch(limitBranch+"01");
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setBd("");
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setCurrency(ISOcurrency);
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setDepartement(limitBranch);
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setDescription("FTI");
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setNotenumber(reservationIdentitifer);
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setQual("0");
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setTran("67");
        soapReqXL31.getBody().getXl31().getCmsXl31Request().setTransactiondate(transactionDate);

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
                String finalResponseCode = cmsResponseXL31.getBody().getXl31Response().getCmsXl31Response().getResponsecode();

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
