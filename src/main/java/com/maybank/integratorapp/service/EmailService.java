package com.maybank.integratorapp.service;

import com.maybank.integratorapp.data.entity.FtiTransaction;
import com.maybank.integratorapp.data.entity.FtiTransactionDetail;
import com.maybank.integratorapp.data.service.FtiTransactionDetailService;
import com.maybank.integratorapp.data.service.FtiTransactionService;
import com.maybank.integratorapp.data.service.MsParameterService;
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
    private String loadTemplate(Map<String, String> variables) {
        // Read the template file
        String template = parameterService.findValueByPrmKey("EmailNotificationTemplate");

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

            String finalMessage = loadTemplate(variables);

            String host = parameterService.findValueByPrmKey("EmailNotificationHost");
            String port = parameterService.findValueByPrmKey("EmailNotificationPort");

            String from = parameterService.findValueByPrmKey("EmailNotificationFrom");
            String to = parameterService.findValueByPrmKey("EmailNotificationTo");
            String dev = parameterService.findValueByPrmKey("EmailNotificationDev");

//            EmailSender emailSender = new EmailSender();

            sendEmail(from,to,finalMessage,"Trade Integration Notification");
            System.out.println("Trx Notif Sent "+header.getMasterRefNo());
        }
        catch (Exception e){
            System.out.println("Trx Notif Failed "+e.getMessage());
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
