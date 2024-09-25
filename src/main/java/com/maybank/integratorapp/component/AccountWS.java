package com.maybank.integratorapp.component;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.maybank.integratorapp.model.soap.accountinformation.response.AccountInformationResponse;
import com.maybank.integratorapp.model.soap.accountinquiry.response.AccountInquiryResponse;
import com.maybank.integratorapp.util.SOAPUtil;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.maybank.integratorapp.model.soap.ChannelHeaderType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

//import com.sun.org.apache.xml.internal.utils.QName;


//@Component
public class AccountWS {
    private static final Logger logger = LogManager.getFormatterLogger(AccountWS.class);

    private final int serviceConnectTimeout = 120000;
    private final int serviceReadTimeout = 120000;

    public AccountWS(){
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate(messageFactory());
        webServiceTemplate.setMessageSender(messageSender());
        webServiceTemplate.setDefaultUri("http://10.235.66.95:7800/AccountServices");

//        webServiceTemplate.setUnmarshaller(jaxb2Marshaller());
        System.out.println("WEB SERVICE TEMPLATE INITIATED");
        this.webServiceTemplate = webServiceTemplate;
    }
    public HttpComponentsMessageSender messageSender() {
        HttpComponentsMessageSender httpComponentsMessageSender = new HttpComponentsMessageSender();
        httpComponentsMessageSender.setConnectionTimeout(serviceConnectTimeout);
        httpComponentsMessageSender.setReadTimeout(serviceReadTimeout);
        return httpComponentsMessageSender;
    }
    public SaajSoapMessageFactory messageFactory() {
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
        messageFactory.afterPropertiesSet();
        return messageFactory;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    @Autowired
    @Qualifier("webServiceTemplateAccount")
    private WebServiceTemplate webServiceTemplate;

    private ChannelHeaderType ch;
    private String inputSoapMessage;

    public ChannelHeaderType getCh() {
        return ch;
    }
    public void setCh(ChannelHeaderType ch) {
        this.ch = ch;
    }
    public String getInputSoapMessage() {
        return inputSoapMessage;
    }
    public void setInputSoapMessage(String inputSoapMessage) {
        this.inputSoapMessage = inputSoapMessage;
    }

    public AccountInquiryResponse callAccountInquiry(String accountNo, String ccyId) {
        String message = "<start></start>";
        String outputResponse = "";
        AccountInquiryResponse response = null;
        StreamSource source = new StreamSource(new StringReader(message));
        ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(outputStream1);


        webServiceTemplate.sendSourceAndReceiveToResult(source, new WebServiceMessageCallback() {
            @Override
            public void doWithMessage(WebServiceMessage arg0) throws IOException, TransformerException {
                // TODO Auto-generated method stub
                SaajSoapMessage saajSoapMessage = (SaajSoapMessage)arg0;
                Transformer identityTransform = null;
                //identityTransform = (Transformer) TransformerFactory.newInstance().newTransformer();

                //fixing fortify
                TransformerFactory trfactory = TransformerFactory.newInstance();
                trfactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                trfactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
                trfactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
                identityTransform = trfactory.newTransformer();


                identityTransform.transform(new DOMSource(null), arg0.getPayloadResult());
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                try {
                    SOAPMessage soapMessage =saajSoapMessage.getSaajMessage();
                    //SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
                    soapMessage.getSOAPPart().getEnvelope().removeNamespaceDeclaration("soapenc");
                    soapMessage.getSOAPPart().getEnvelope().removeNamespaceDeclaration("xsd");
                    soapMessage.getSOAPPart().getEnvelope().removeNamespaceDeclaration("xsi");
                    soapMessage.getSOAPPart().getEnvelope().removeNamespaceDeclaration(soapMessage.getSOAPPart().getEnvelope().getPrefix());
                    soapMessage.getSOAPPart().getEnvelope().addNamespaceDeclaration("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
                    soapMessage.getSOAPPart().getEnvelope().setPrefix("soapenv");
                    soapMessage.getSOAPPart().getEnvelope().addNamespaceDeclaration("acc", "http://www.bankbii.com/AccountServices/");
                    soapMessage.getSOAPHeader().setPrefix("soapenv");
                    soapMessage.getSOAPBody().setPrefix("soapenv");

                    SOAPUtil soapUtil = new SOAPUtil();

                    QName method = new QName("http://www.bankbii.com/AccountServices/","AccountInquiry","acc");
                    SOAPElement param = soapMessage.getSOAPBody().addChildElement(method);


                    SOAPElement channelHead = soapUtil.setProperties(param, "ChannelHeader",  "","","");
                    soapUtil.setProperties(channelHead, "messageID",  "",ch.getMessageID(),"");
//                    soapUtil.setProperties(channelHead, "additionalHeader",  "",ch.getAdditionalHeader(),"");
                    soapUtil.setProperties(channelHead, "branchCode",  "",ch.getBranchCode(),"");
                    soapUtil.setProperties(channelHead, "channelID",  "",ch.getChannelID(),"");
                    soapUtil.setProperties(channelHead, "clientSupervisorID",  "",ch.getClientSupervisorID(),"");
                    soapUtil.setProperties(channelHead, "clientUserID",  "",ch.getClientUserID(),"");
                    soapUtil.setProperties(channelHead, "reference",  "",ch.getReference(),"");
                    soapUtil.setProperties(channelHead, "sequenceno",  "",ch.getSequenceno(),"");
                    soapUtil.setProperties(channelHead, "transactiondate",  "",ch.getTransactiondate(),"");
                    soapUtil.setProperties(channelHead, "transactiontime",  "",ch.getTransactiontime(),"");

                    SOAPElement dataElemet = soapUtil.setProperties(param, "AccountInquiryRequest", "", "", "");

                    //soapUtil.setProperties(dataElemet, "GCIFNo",  "", data.getGcifNo(),"");
//	    			SOAPElement dataAccount = soapUtil.setProperties(dataElemet, "Account", "", "", "");
                    soapUtil.setProperties(dataElemet, "accountNo",  "",accountNo,"");
                    soapUtil.setProperties(dataElemet, "accountBranchCode",  "",accountNo.substring(1,4),"");
                    soapUtil.setProperties(dataElemet, "accountCurrency",  "",ccyId,"");
//                    soapUtil.setProperties(dataElemet, "isOri",  "","1","");


                    soapMessage.writeTo(outputStream);
                    String output = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);

                    //logger.info("XML Request AccountList " +System.currentTimeMillis()+" ---- "+output);
                    logger.info("XML Request AccountInformation " +System.currentTimeMillis()+" ---- "+output);
                    setInputSoapMessage(output);
                }
                catch(SOAPException e) {
                    logger.error("SOAPException in AccountWS, method [doWithMessage]" + ", error = ", e);
                }
//	    		finally {
//
////	    			if(outputStream !=null) {
////	    				outputStream.close();
////	    			}
//	    		}
                outputStream.close();

            }
        },result);

        outputResponse = new String(outputStream1.toByteArray(),StandardCharsets.UTF_8);
        System.out.println(outputResponse);
        XmlMapper mapper = new XmlMapper();
        try {
            response = mapper.readValue(outputResponse, AccountInquiryResponse.class);
            outputResponse = response.getResponseData().getBalance();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

//        JAXBContext jaxbContext = null;
//        try {
//            jaxbContext = JAXBContext.newInstance(Envelope.class);
//            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//            Envelope envelope = (Envelope) unmarshaller.unmarshal(new StringReader(outputResponse));
//            logger.info(envelope.getBody().getAccountInquiryResponse().getAccountInquiryResponseData().getBalance());
//        } catch (JAXBException e) {
//            throw new RuntimeException(e);
//        }
//        Envelope envelope =  (Envelope) jaxb2Marshaller().unmarshal(new StreamSource(new StringReader(outputResponse)));
//        if(envelope.getBody() != null){
//            outputResponse = envelope.getBody().getAccountInquiryResponse().getAccountInquiryResponseData().getBalance();
//
//        }
//        logger.info("RESPONSE AQUIRED : "+.getAccountInquiry());
        //logger.info("XML Response AccountList " +System.currentTimeMillis()+" ---- "+outputResponse);
        logger.info("XML Response AccountInformation " +System.currentTimeMillis()+" ---- "+outputResponse);
        try {
            outputStream1.close();
        }
        catch (IOException e) {
            logger.error("IOException in AccountWS, method [callAccountList, finally]" + ", error = ", e);
        }

//		try {
//
//		}
//		catch(Exception e) {
//			logger.error("Exception in AccountWS, method [callAccountList]" + ", error = ", e);
//		}
//		finally {
//			if(outputStream1 != null) {
//				try {
//					outputStream1.close();
//				}
//				catch (IOException e) {
//					logger.error("IOException in AccountWS, method [callAccountList, finally]" + ", error = ", e);
//				}
//			}
//		}
        return response;
    }

    public AccountInformationResponse callAccountInformation(String accountNo, String ccyId) {
        String message = "<start></start>";
        String outputResponse = "";
        AccountInformationResponse response = null;
        StreamSource source = new StreamSource(new StringReader(message));
        ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(outputStream1);


        webServiceTemplate.sendSourceAndReceiveToResult(source, new WebServiceMessageCallback() {
            @Override
            public void doWithMessage(WebServiceMessage arg0) throws IOException, TransformerException {
                // TODO Auto-generated method stub
                SaajSoapMessage saajSoapMessage = (SaajSoapMessage)arg0;
                Transformer identityTransform = null;
                //identityTransform = (Transformer) TransformerFactory.newInstance().newTransformer();

                //fixing fortify
                TransformerFactory trfactory = TransformerFactory.newInstance();
                trfactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                trfactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
                trfactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
                identityTransform = trfactory.newTransformer();


                identityTransform.transform(new DOMSource(null), arg0.getPayloadResult());
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                try {
                    SOAPMessage soapMessage =saajSoapMessage.getSaajMessage();
                    //SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
                    soapMessage.getSOAPPart().getEnvelope().removeNamespaceDeclaration("soapenc");
                    soapMessage.getSOAPPart().getEnvelope().removeNamespaceDeclaration("xsd");
                    soapMessage.getSOAPPart().getEnvelope().removeNamespaceDeclaration("xsi");
                    soapMessage.getSOAPPart().getEnvelope().removeNamespaceDeclaration(soapMessage.getSOAPPart().getEnvelope().getPrefix());
                    soapMessage.getSOAPPart().getEnvelope().addNamespaceDeclaration("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
                    soapMessage.getSOAPPart().getEnvelope().setPrefix("soapenv");
                    soapMessage.getSOAPPart().getEnvelope().addNamespaceDeclaration("acc", "http://www.bankbii.com/AccountServices/");
                    soapMessage.getSOAPHeader().setPrefix("soapenv");
                    soapMessage.getSOAPBody().setPrefix("soapenv");

                    SOAPUtil soapUtil = new SOAPUtil();

                    QName method = new QName("http://www.bankbii.com/AccountServices/","AccountInformation","acc");
                    SOAPElement param = soapMessage.getSOAPBody().addChildElement(method);


                    SOAPElement channelHead = soapUtil.setProperties(param, "ChannelHeader",  "","","");
                    soapUtil.setProperties(channelHead, "messageID",  "",ch.getMessageID(),"");
//                    soapUtil.setProperties(channelHead, "additionalHeader",  "",ch.getAdditionalHeader(),"");
                    soapUtil.setProperties(channelHead, "branchCode",  "",ch.getBranchCode(),"");
                    soapUtil.setProperties(channelHead, "channelID",  "",ch.getChannelID(),"");
                    soapUtil.setProperties(channelHead, "clientSupervisorID",  "",ch.getClientSupervisorID(),"");
                    soapUtil.setProperties(channelHead, "clientUserID",  "",ch.getClientUserID(),"");
                    soapUtil.setProperties(channelHead, "reference",  "",ch.getReference(),"");
                    soapUtil.setProperties(channelHead, "sequenceno",  "",ch.getSequenceno(),"");
                    soapUtil.setProperties(channelHead, "transactiondate",  "",ch.getTransactiondate(),"");
                    soapUtil.setProperties(channelHead, "transactiontime",  "",ch.getTransactiontime(),"");

                    SOAPElement dataElemet = soapUtil.setProperties(param, "AccountInformationRequest", "", "", "");

                    //soapUtil.setProperties(dataElemet, "GCIFNo",  "", data.getGcifNo(),"");
//	    			SOAPElement dataAccount = soapUtil.setProperties(dataElemet, "Account", "", "", "");
                    soapUtil.setProperties(dataElemet, "accountNo",  "",accountNo,"");
                    soapUtil.setProperties(dataElemet, "accountBranchCode",  "",accountNo.substring(1,4),"");
                    soapUtil.setProperties(dataElemet, "accountCurrency",  "",ccyId,"");
                    soapUtil.setProperties(dataElemet, "isOri",  "","1","");
//                    soapUtil.setProperties(dataElemet, "isOri",  "","1","");


                    soapMessage.writeTo(outputStream);
                    String output = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);

                    //logger.info("XML Request AccountList " +System.currentTimeMillis()+" ---- "+output);
                    logger.info("XML Request AccountInformation " +System.currentTimeMillis()+" ---- "+output);
                    setInputSoapMessage(output);
                }
                catch(SOAPException e) {
                    logger.error("SOAPException in AccountWS, method [doWithMessage]" + ", error = ", e);
                }
//	    		finally {
//
////	    			if(outputStream !=null) {
////	    				outputStream.close();
////	    			}
//	    		}
                outputStream.close();

            }
        },result);

        outputResponse = new String(outputStream1.toByteArray(),StandardCharsets.UTF_8);
        System.out.println(outputResponse);
        XmlMapper mapper = new XmlMapper();
        try {
            response = mapper.readValue(outputResponse, AccountInformationResponse.class);
            outputResponse = response.getAccountInformationResponseData().getAccountData().getCADataRecord().getAccounttype();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

//        JAXBContext jaxbContext = null;
//        try {
//            jaxbContext = JAXBContext.newInstance(Envelope.class);
//            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//            Envelope envelope = (Envelope) unmarshaller.unmarshal(new StringReader(outputResponse));
//            logger.info(envelope.getBody().getAccountInquiryResponse().getAccountInquiryResponseData().getBalance());
//        } catch (JAXBException e) {
//            throw new RuntimeException(e);
//        }
//        Envelope envelope =  (Envelope) jaxb2Marshaller().unmarshal(new StreamSource(new StringReader(outputResponse)));
//        if(envelope.getBody() != null){
//            outputResponse = envelope.getBody().getAccountInquiryResponse().getAccountInquiryResponseData().getBalance();
//
//        }
//        logger.info("RESPONSE AQUIRED : "+.getAccountInquiry());
        //logger.info("XML Response AccountList " +System.currentTimeMillis()+" ---- "+outputResponse);
        logger.info("XML Response AccountInformation " +System.currentTimeMillis()+" ---- "+outputResponse);
        try {
            outputStream1.close();
        }
        catch (IOException e) {
            logger.error("IOException in AccountWS, method [callAccountList, finally]" + ", error = ", e);
        }

//		try {
//
//		}
//		catch(Exception e) {
//			logger.error("Exception in AccountWS, method [callAccountList]" + ", error = ", e);
//		}
//		finally {
//			if(outputStream1 != null) {
//				try {
//					outputStream1.close();
//				}
//				catch (IOException e) {
//					logger.error("IOException in AccountWS, method [callAccountList, finally]" + ", error = ", e);
//				}
//			}
//		}
        return response;
    }
}
