package com.maybank.integratorapp.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@Service
public class SendingEmailServiceImpl {

    private static Logger log = LoggerFactory.getLogger(SendingEmailServiceImpl.class);

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String from,String to,String body,String subject) throws MessagingException, IOException {



        MimeMessage message 				= emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
//        mimeMessageHelper.addAttachment("logo.png", new ClassPathResource("maybank_logo.png"));

        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setText(body, true);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setFrom(from);
        emailSender.send(message);
        log.info("Sending mail successfully...");

    }
}
