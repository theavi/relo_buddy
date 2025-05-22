package com.rlb.notification.controller;

import com.rlb.notification.service.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-email")
public class EmailTestingController {

    private final EmailService emailService;

    public EmailTestingController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/")
    public String sendEmailTest(){
        emailService.sendEmail("icible0@gmail.com","Email Test", "This is email");
        return "Email Test Passed";
    }
}
