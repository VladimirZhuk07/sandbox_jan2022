package com.exadel.sandbox.team2.notification.mail.service;

import com.exadel.sandbox.team2.notification.mail.dto.MailDto;
import com.exadel.sandbox.team2.notification.mail.service.impl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.springframework.scheduling.annotation.Async;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSender {

    private final EmailServiceImpl emailService;

    @Async
    public void send(MailDto message) {
        try {
            log.info("Send email called.");
            emailService.sendMail(message);
        } catch (Exception e) {
            log.error("Error during sending of email.", e);
        }
    }
}
