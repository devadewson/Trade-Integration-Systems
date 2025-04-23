package com.maybank.integratorapp.component.system.messageprocessor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.maybank.integratorapp.data.entity.FtiTransactionDetail;
import com.maybank.integratorapp.data.entity.MsBranch;
import com.maybank.integratorapp.data.entity.MsCompanyLimit;
import com.maybank.integratorapp.data.repository.MsParameterRepository;
import com.maybank.integratorapp.data.service.*;
import com.maybank.integratorapp.model.mq.reservationsreversal.response.ResponseHeader;
import com.maybank.integratorapp.model.mq.reservationsreversal.request.ServiceRequest;
import com.maybank.integratorapp.model.mq.reservationsreversal.response.ReservationsReversalResponse;
import com.maybank.integratorapp.model.mq.reservationsreversal.response.ServiceResponse;
import com.maybank.integratorapp.model.soap.limit.XL41.request.SoapEnvelope;
import jakarta.jms.JMSException;
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

@Component
public class LimitReversalMessageProcessor {

    @Autowired
    LogInterfaceProcessService logger;
    @Autowired
    MsParameterRepository parameterRepository;
    @Autowired
    private MsParameterService parameterService;
    @Autowired
    private FtiTransactionDetailService ftiTransactionDetailService;
    @Autowired
    MsCurrencyService msCurrencyService;
    @Autowired
    MsBranchService msBranchService;
    ServiceResponse response = new ServiceResponse();

    private final String ProcessName = "LimitReversalProcess";
    @Autowired
    MsCompanyLimitService msCompanyLimitService;


    public String processMessage(String message,Long loggerId) {
        String responseXml = "";
        logger.SetLogParent(loggerId);

        try {
            // step 1. Parse request message
            // step 2. Set initial response
            // step 3. Map external request to core system request
            // step 4. Request data from core system
            // step 5. Map core system data to external Response
            // step 6. return response message as string

            // step 1.
            ServiceRequest request = parseRequest(message);

            if (request != null) {

                // step 2.
                setInitialResponseHeader(request);

//                String utilizationID = request.getBatchRequest().getServiceRequestChild().get(0).getExposure().getReservationIdentifier();
//                String correlationID = request.getRequestHeader().getCorrelationID();
//                String masterReference = request.getBatchRequest().getServiceRequestChild().get(0).getExposure().getMasterReference();
//                String eventCode = request.getBatchRequest().getServiceRequestChild().get(0).getExposure().getEventReference();
//                String customerRes = request.getReservationsReversalRequest().getCustomer();
//            String customerRes = "0002794045";
                String correlationID = request.getRequestHeader().getCorrelationID();
                String masterReference= request.getReservationsReversalRequest().getMasterReference();
                String utilizationID  = request.getReservationsReversalRequest().getReservationIdentifier();
                String eventCode = request.getReservationsReversalRequest().getEventReference();


                // step 3.
                SoapEnvelope msgRequest = mapCoreSystemRequest(utilizationID,correlationID,eventCode);

                // step 4.
                com.maybank.integratorapp.model.soap.limit.XL41.response.SoapEnvelope msgResponse = getMsgBodyResponse(msgRequest,eventCode,masterReference,utilizationID);

                // step 5.
//                mapExternalResponse(msgResponse,msgRequest);

            }

            // step 6.
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
            responseXml = xmlMapper.writeValueAsString(response);

        } catch (Exception e) {
            logger.Log(ProcessName, "Error Processing Message", "ERROR-PROCESS-MESSAGE", e.getMessage());

        }
        return responseXml;
    }

    private void handleExceptionResponse(String message) {
//        response.getResponseHeader().setStatus("Error");
//        response.getResponseHeader().getDetails().setError(message);
    }

    // step 1. Parse request message
    private ServiceRequest parseRequest(String message) throws JMSException {
        XmlMapper xmlMapper = new XmlMapper();
        ServiceRequest request;
        try {
            request = xmlMapper.readValue(message, ServiceRequest.class);
        } catch (JsonProcessingException e) {
            logger.Log(ProcessName, "Error Json Processing", "ERROR-JSON-PROCESS", e.getMessage());
            handleExceptionResponse(e.getMessage());
            request = null;
        }
        return request;
    }

    // step 2. Set initial response
    private void setInitialResponseHeader(ServiceRequest request) {
        response.setResponseHeader(new ResponseHeader());
        response.getResponseHeader().setCorrelationID(request.getRequestHeader().getCorrelationID());
        response.getResponseHeader().setService(request.getRequestHeader().getService());
        response.getResponseHeader().setOperation(request.getRequestHeader().getOperation());
        response.getResponseHeader().setSourceSystem(request.getRequestHeader().getTargetSystem());
        response.getResponseHeader().setTargetSystem(request.getRequestHeader().getSourceSystem());
        response.getResponseHeader().setStatus("SUCCEEDED");

        response.setReservationsReversalResponse(null);
    }

    // step 3. Map external request to core system request
    public SoapEnvelope mapCoreSystemRequest(String utilizationID,String correlationID,String eventCode) {
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

        MsCompanyLimit _company = msCompanyLimitService.searchByCIFNo(limitCif);
        MsBranch _branch = msBranchService.getByBranchCode(limitBranch);

        String clientUserId = "7755";
        String clientSpvUserId = "7766";

        if(_branch!=null){
            clientUserId = _branch.getUserId();
            clientSpvUserId = _branch.getSpvUserId();
        }

        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setAdditionalHeader("");
        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setBranchCode(limitBranch);
        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setChannelID(clsChannelId);
        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setClientUserID(clientUserId);
        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setReference(eventCode);
        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setTransactionDate(date);
        soapEnvelopeXL41.getBody().getXl41().getChannelHeader().setTransactionTime(time);

        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setCtl2(limitCurrency);
        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setCtl3(limitBranch);
        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setCust(limitCif);
        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setDraw(limitDraw);
        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setFlag("D");
        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setNote(limitNoteKey);
        soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().setPart("99");

        return soapEnvelopeXL41;
    }
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
    // step 4. Request data from core system
    public com.maybank.integratorapp.model.soap.limit.XL41.response.SoapEnvelope getMsgBodyResponse(SoapEnvelope soapEnvelopeXL41,String eventCode,String referenceId,String reservationId) {
        com.maybank.integratorapp.model.soap.limit.XL41.response.SoapEnvelope res = new com.maybank.integratorapp.model.soap.limit.XL41.response.SoapEnvelope();
        String soapUrl = parameterService.findValueByPrmKey("XL41Request");
        String dcType = soapEnvelopeXL41.getBody().getXl41().getCmsXl41Request().getFlag();
        String amount = "-";
        String noteNumber = reservationId;

        try {
            XmlMapper mapper = new XmlMapper();

            // Avoid unnecessary wrapping
            mapper.setDefaultUseWrapper(false);
            mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

            String xmlString = null;
            try {
                xmlString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(soapEnvelopeXL41);

                System.out.println(xmlString);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            logger.Log(ProcessName, "Hit ESB Message", "ESB-MESSAGE", xmlString);

            com.maybank.integratorapp.model.soap.limit.XL41.response.SoapEnvelope cmsResponseXL41 = null;

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(soapUrl);
                httpPost.setHeader("Content-Type", "text/xml");
                httpPost.setEntity(new StringEntity(xmlString, ContentType.TEXT_XML));
                String _response = "";

                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {

                    // Handle response if needed
                    var _res = response.getEntity();
                    var _resStream = _res.getContent();
                    var outputResponse = new String(_resStream.readAllBytes(), StandardCharsets.UTF_8);

                    _response = outputResponse;
                    logger.Log(ProcessName, "Response ESB Message", "ESB-MESSAGE", _response);

                    cmsResponseXL41 = mapper.readValue(_response, com.maybank.integratorapp.model.soap.limit.XL41.response.SoapEnvelope.class);
                    String responseCode = cmsResponseXL41
                            .getBody().getXl41Response().
                            getCmsXl41Response().getResponsecode();
                    String responseMessage = cmsResponseXL41
                            .getBody().getXl41Response().
                            getCmsXl41Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("general_message")).findFirst().get().getValue();
                    String responseMessage2 = cmsResponseXL41
                            .getBody().getXl41Response().
                            getCmsXl41Response().getResponseDetail().getAdditionalData().stream().filter(x -> x.getParam().equals("status1")).findFirst().get().getValue();

                    FtiTransactionDetail ftiTransactionDetail = new FtiTransactionDetail();
                    ftiTransactionDetail.setTransMessageLogId(logger.getIdLogParent());
                    ftiTransactionDetail.setFtiEvent(eventCode);
                    ftiTransactionDetail.setCoreSysName("CLS-XL41");
                    ftiTransactionDetail.setTransName("Reversal");
                    ftiTransactionDetail.setCoreSysStatus(responseCode);
                    ftiTransactionDetail.setCoreSysMessage(responseMessage2);
                    ftiTransactionDetail.setAdditionalInfo1(noteNumber);
                    ftiTransactionDetail.setAdditionalInfo2(dcType);
                    ftiTransactionDetail.setAdditionalInfo3(amount);
                    ftiTransactionDetail.setAdditionalInfo4("DEL");
                    ftiTransactionDetailService.createDetailByMasterRefNo(referenceId, ftiTransactionDetail);

                    String xmlResponseXL41 = mapper.writeValueAsString(cmsResponseXL41);
                    System.out.println(xmlResponseXL41);

                }
            } catch (Exception e) {
                logger.Log(ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());

            }
        } catch (Exception e) {
            logger.Log(ProcessName, "Error Hit ESB Message", "ESB-MESSAGE", e.getMessage());

        }
        return res;
    }

    // step 5. Map core system data to external Response
//    private void mapExternalResponse(com.maybank.integratorapp.model.restv2.AccountInquiry.response.MsgWraper res,MsgWraper req) {
//
//
//        response.setAvailBalResponse(availBalResponse);
//    }
}
