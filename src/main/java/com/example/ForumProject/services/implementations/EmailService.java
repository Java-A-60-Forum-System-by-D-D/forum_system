package com.example.ForumProject.services.implementations;

import org.apache.commons.mail.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    public String emailUsername;

    @Value("${spring.mail.password}")
    public String emailPassword;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        Email email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(587);
        email.setAuthenticator(new DefaultAuthenticator(emailUsername, emailPassword));
        email.setSSLOnConnect(true);
        try {
            email.setFrom("forum@ddforum.com");
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
        email.setSubject(subject);
        try {
            email.setMsg(text);
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
        try {
            email.addTo(to);
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
        try {
            email.send();
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendRegistrationEmail(String to) {
        String subject = "Welcome to the Forum!";
        String text = "Thank you for registering at our forum. We are excited to have you!";
        sendEmail(to, subject, text);
    }
    public void sendCommentNotification(String to, String postTitle) {
        String subject = "New comment on " + postTitle;
        String text = "There is a new comment on the post " + postTitle;
        sendEmail(to, subject, text);
    }

}
