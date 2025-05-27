package com.maybank.integratorapp.util;

import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmailSender {
    public void sendEmail(String smtpHost,String smtpPort,String fromEmail ,String toEmail, String subject, String htmlContent) {
        // Set up mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true"); // Enable TLS

        // Create a session with an authenticator
        Session session = Session.getInstance(props);
//        Session session = Session.getInstance(props, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });

        try {
            Address _fromAddress = new InternetAddress(fromEmail);

            List<Address> _listToAddress = new ArrayList<>();
            for (String email : toEmail.split(";")) {
                Address _toAddress = new InternetAddress(email);
                _listToAddress.add(_toAddress);
            }


            // Create a MimeMessage object
            Message message = new MimeMessage(session);
            message.setFrom(_fromAddress);
            message.setRecipients(MimeMessage.RecipientType.TO, (Address[]) _listToAddress.toArray());

            // Set the sender and recipient email addresses
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            // Set the email subject and content
            message.setSubject(subject);
            message.setContent(htmlContent, "text/html");

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully to " + toEmail);

        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("Failed to send email " + e.getMessage());
        }
    }
}
