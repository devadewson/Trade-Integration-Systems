package com.maybank.integratorapp.component.coresystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.data.entity.FtiTransactionDetail;
import com.maybank.integratorapp.data.service.FtiTransactionDetailService;
import com.maybank.integratorapp.data.service.MsParameterService;
import com.maybank.integratorapp.model.soap.XL41.request.SoapEnvelope;
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
import java.util.Date;
@Component
public class ProcessLimitUtilization {
    @Autowired
    private MsParameterService parameterService;
    @Autowired
    private FtiTransactionDetailService ftiTransactionDetailService;

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
    public String getLimitUtilization(String AccountNo,String utilizationID, String correlationID,String eventCode,String referenceId) {

        String soapUrl = parameterService.findValueByPrmKey("XL41Request");
        String clsChannelId = parameterService.findValueByPrmKey("CLSChannelId");
//        String soapUrl = "http://10.230.83.57:65085/services/CMSService";
//        String correlationID = "ServiceRequest.getRequestHeader().getCorrelationID();";
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        SoapEnvelope soapEnvelopeXL41 = new SoapEnvelope();

        String[] splittedKey = splitKey(utilizationID);
        String limitCurrency = splittedKey[1];
        String limitBranch = splittedKey[2];
        String limitCif = splittedKey[3];
        String limitNoteKey = splittedKey[4];
        String limitDraw = splittedKey[5];
        String dateNow = new SimpleDateFormat("ddMMyy").format(new Date());

        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setAdditionalHeader("");
        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setBranchCode("003");
        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setChannelID(clsChannelId);
        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setClientUserID("7755");
        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setReference(correlationID);
        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setTransactionDate(date);
        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setTransactionTime(time);

        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setCtl2(limitCurrency);
        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setCtl3(limitBranch);
        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setCust(limitCif);
        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setDraw(limitDraw);
        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setFlag("P");
        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setNote(limitNoteKey);
        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setPart("99");

        XmlMapper mapper = new XmlMapper();

        // Avoid unnecessary wrapping
        mapper.setDefaultUseWrapper(false);
        mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

        String xmlXl41 = null;
        try {
            xmlXl41 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(soapEnvelopeXL41);

            System.out.println(xmlXl41);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        com.maybank.integratorapp.model.soap.XL41.response.SoapEnvelope cmsResponseXL41 = null;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(soapUrl);
            httpPost.setHeader("Content-Type", "text/xml");
            httpPost.setEntity(new StringEntity(xmlXl41, ContentType.TEXT_XML));
            String _response = "";

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                // Handle response if needed
                var _res = response.getEntity();
                var _resStream = _res.getContent();
                var outputResponse = new String(_resStream.readAllBytes(), StandardCharsets.UTF_8);

                _response = outputResponse;
                if(_response.contains("Fault")){
                    System.out.println(_response);
                    return "failed";
                }
                cmsResponseXL41 = mapper.readValue(_response, com.maybank.integratorapp.model.soap.XL41.response.SoapEnvelope.class);
                String responseCode = cmsResponseXL41
                        .getBody().getXl41Response().
                        getCmsXl41Response().getResponsecode();
                String responseMessage = cmsResponseXL41
                        .getBody().getXl41Response().
                        getCmsXl41Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();


                FtiTransactionDetail ftiTransactionDetail = new FtiTransactionDetail();
//                ftiTransactionDetail.setTransMessageLogId(logger.getIdLogParent());
                ftiTransactionDetail.setFtiEvent(eventCode);
                ftiTransactionDetail.setCoreSysName("CLS-XL41");
                ftiTransactionDetail.setTransName("Utilization");
                ftiTransactionDetail.setCoreSysStatus(responseCode);
                ftiTransactionDetail.setCoreSysMessage(responseMessage);
                ftiTransactionDetailService.createDetailByMasterRefNo(referenceId, ftiTransactionDetail);

                String xmlResponseXL41 = mapper.writeValueAsString(cmsResponseXL41);
                System.out.println(xmlResponseXL41);

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        // Return objek CMS_XL31Response
        return "sukses";
    }

}
