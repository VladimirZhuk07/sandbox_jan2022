package com.exadel.sandbox.team2.notification.mail.service;

import com.exadel.sandbox.team2.notification.mail.dto.MailDto;

public interface EmailService {
    void sendMail(MailDto mailDto);

}
