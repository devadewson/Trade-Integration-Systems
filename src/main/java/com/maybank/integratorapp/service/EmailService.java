package com.maybank.integratorapp.service;

import com.maybank.integratorapp.data.entity.FtiTransaction;
import com.maybank.integratorapp.data.entity.FtiTransactionDetail;
import com.maybank.integratorapp.data.entity.FtiTransactionDetailPosting;
import com.maybank.integratorapp.data.entity.FtiTransactionDetailPostingGroup;
import com.maybank.integratorapp.data.service.*;
//import com.maybank.integratorapp.util.EmailSender;
import com.maybank.integratorapp.util.EmailSender;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class EmailService {
    @Autowired
    MsParameterService parameterService;
    private static Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    FtiTransactionService ftiTransactionService;

    @Autowired
    FtiTransactionDetailPostingGroupService ftiTransactionDetailPostingGroupService;

    @Autowired
    FtiTransactionDetailPostingService ftiTransactionDetailPostingService;

    private String loadTemplate(Map<String, String> variables,String template) {

        // Replace placeholders with actual values
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            template = template.replace("${" + entry.getKey() + "}", entry.getValue());
        }

        return template;
    }
    public void sendTransactionNotification(FtiTransactionDetail detail){
        try {

            // get transaction master

            FtiTransaction header = ftiTransactionService.getFtiTransactionById(detail.getHeaderId());
            Map<String,String> variables = new HashMap<>();
            variables.put("transRef",header.getMasterRefNo());
            variables.put("transEvent",detail.getFtiEvent());
            variables.put("transStep",detail.getTransName());
            variables.put("coreSysName",detail.getCoreSysName());
            variables.put("coreSysFlag",detail.getAdditionalInfo1());
            variables.put("coreSysCode",detail.getCoreSysStatus());
            variables.put("coreSysMessage",detail.getCoreSysMessage());
            String template = parameterService.findValueByPrmKey("EmailNotificationTemplate");

            String finalMessage = loadTemplate(variables,template);

            String host = parameterService.findValueByPrmKey("EmailNotificationHost");
            String port = parameterService.findValueByPrmKey("EmailNotificationPort");

            String from = parameterService.findValueByPrmKey("EmailNotificationFrom");
            String to = parameterService.findValueByPrmKey("EmailNotificationTo");
            String dev = parameterService.findValueByPrmKey("EmailNotificationDev");

//            EmailSender emailSender = new EmailSender();

            sendEmail(from,to,finalMessage,"Trade Integration Notification");
             log.info("Trx Notif Sent "+header.getMasterRefNo());
        }
        catch (Exception e){
             log.error("Trx Notif Failed "+e.getMessage());
        }
    }

    public void sendTransactionNotification(FtiTransaction header,
                                            List<FtiTransactionDetail> transactionDetailList){
        try {


//            FtiTransaction header = ftiTransactionService.getFtiTransactionById(detail.getHeaderId());
            Map<String,String> variables = new HashMap<>();
            variables.put("transRef",header.getMasterRefNo());
            variables.put("transEvent",transactionDetailList.get(0).getFtiEvent());

            String finalMessage = loadTemplate(variables,"EmailNotificationTemplateHeader");

            StringBuilder detailMessage = new StringBuilder();
            for (FtiTransactionDetail detail:
                 transactionDetailList) {
                variables = new HashMap<>();

                variables.put("transStep",detail.getTransName());
                variables.put("coreSysName",detail.getCoreSysName());
                variables.put("coreSysFlag",detail.getAdditionalInfo1());
                variables.put("coreSysCode",detail.getCoreSysStatus());
                variables.put("coreSysMessage",detail.getCoreSysMessage());
                String template = parameterService.findValueByPrmKey("EmailNotificationTemplateDetail");

                String _detailMessage = loadTemplate(variables, template);

                if(ftiTransactionDetailPostingGroupService.getByDetailId(detail.getId()).get(0)!= null){
                    String _postingMessage = "";
                    String _postingDetailMessage = "";
                    FtiTransactionDetailPostingGroup _postingGroup = ftiTransactionDetailPostingGroupService.getByDetailId(detail.getId()).get(0);
                    List<FtiTransactionDetailPosting> _postings = ftiTransactionDetailPostingService.getByIdGroup(_postingGroup.getId());
                    template = parameterService.findValueByPrmKey("EmailNotificationTemplateDetail");
                    for (FtiTransactionDetailPosting posting:
                            _postings) {
                        variables = new HashMap<>();
                        variables.put("postingDebitCredit",posting.getDebitCredit());
                        variables.put("postingAccount",posting.getAccount());
                        variables.put("postingCurrency",posting.getCcy());
                        variables.put("postingAmount",posting.getAmount());
                        _postingDetailMessage+= loadTemplate(variables,template);

                    }
                    variables = new HashMap<>();
                    variables.put("postingDetail",_postingDetailMessage);
                    template = parameterService.findValueByPrmKey("EmailNotificationTemplatePosting");
                    _postingMessage = loadTemplate(variables,template);

                    variables = new HashMap<>();
                    variables.put("templatePosting",_postingMessage);
                    _detailMessage = loadTemplate(variables,_detailMessage);
                }else{
                    variables = new HashMap<>();
                    variables.put("templatePosting","");
                    _detailMessage = loadTemplate(variables,_detailMessage);
                }

                detailMessage.append(_detailMessage);

            }

            variables = new HashMap<>();
            variables.put("templateBody",detailMessage.toString());

            finalMessage = loadTemplate(variables,finalMessage);

            String host = parameterService.findValueByPrmKey("EmailNotificationHost");
            String port = parameterService.findValueByPrmKey("EmailNotificationPort");

            String from = parameterService.findValueByPrmKey("EmailNotificationFrom");
            String to = parameterService.findValueByPrmKey("EmailNotificationTo");
            String dev = parameterService.findValueByPrmKey("EmailNotificationDev");

//            EmailSender emailSender = new EmailSender();

            sendEmail(from,to,finalMessage,"Trade Integration Notification");
            log.info("Trx Notif Sent "+header.getMasterRefNo());
        }
        catch (Exception e){
            log.error("Trx Notif Failed "+e.getMessage());
        }
    }

    public void sendEmail(String from,String to,String body,String subject){

        try {

            MimeMessage message 				= emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        //        mimeMessageHelper.addAttachment("logo.png", new ClassPathResource("maybank_logo.png"));

            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setText(body, true);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setFrom(from);
            emailSender.send(message);
            log.info("Sending mail successfully...");
        }catch (Exception e){
            log.error("Sending mail error..." +e.getMessage());
        }


    }

}
