package com.exadel.sandbox.team2.notification.mail.service;

import com.exadel.sandbox.team2.notification.mail.service.impl.EmailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

@Slf4j
public class NotificationSender {
    @Autowired
    private EmailServiceImpl emailService;

    @Async
    public void send(Runnable sendCallback) {
        try {
            log.info("NotificationSender class, send method called.");
            emailService.sendMail();
        } catch (Exception e) {
            log.error("Error in NotificationSender.class, in method send: {}", e.getMessage());
        }
    }
}
