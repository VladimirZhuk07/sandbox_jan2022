package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.notification.mail.dto.MailDto;
import com.exadel.sandbox.team2.notification.mail.service.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mails")
@RequiredArgsConstructor
public class MailController {

    private final EmailSender emailSender;

    @PostMapping
    public void sendEmail(@RequestBody MailDto mailDto) {
        emailSender.send(mailDto);
    }

    @GetMapping("/activate/{code}")
    public void activate(@PathVariable String code){
    }



}
