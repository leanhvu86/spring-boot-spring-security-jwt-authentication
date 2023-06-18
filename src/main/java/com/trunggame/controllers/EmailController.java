package com.trunggame.controllers;

import com.trunggame.dto.EmailRequest;
import com.trunggame.security.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;

@Controller
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private  MailService mailService;

    @PostMapping("/send")
    @ResponseBody
    public String sendEmail(@RequestBody EmailRequest request) {
        String recipientEmail = request.getRecipientEmail();
        String subject = request.getSubject();

        Context context = new Context();
        context.setVariable("title", request.getTitle());
        context.setVariable("message", request.getMessage());

        mailService.sendEmailWithTemplate(recipientEmail, subject, "email-template", context);

        return "Email sent successfully!";
    }
}
