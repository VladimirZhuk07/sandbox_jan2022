package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.notification.mail.dto.MailDto;
import com.exadel.sandbox.team2.notification.mail.service.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mails")
@RequiredArgsConstructor
public class MailController {

    private final EmailSender emailSender;

    @PostMapping
    public void sendEmail(@RequestBody MailDto mailDto) {
        emailSender.send(mailDto);  
    }


}
