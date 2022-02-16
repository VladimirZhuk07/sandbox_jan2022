package com.exadel.sandbox.team2.notification.gmail.controller;

import com.exadel.sandbox.team2.notification.gmail.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class NotificationSender extends Thread {
    @Autowired
    private EmailService emailService;

    @Override
    public void run() {
        log.info("NotificationSender class called.");
        emailService.sendMail();
    }
}
