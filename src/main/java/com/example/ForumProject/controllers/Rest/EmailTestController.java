package com.example.ForumProject.controllers.Rest;

import com.example.ForumProject.services.implementations.EmailService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailTestController {
    private final EmailService emailService;

    public EmailTestController(EmailService emailService) {
        this.emailService = emailService;
    }
    @RequestMapping("/sendEmail")
    public String sendEmail() {
        emailService.sendEmail("deszafirova@gmail.com", "Test", "Test");
        return "Test email send successfully";
}}
