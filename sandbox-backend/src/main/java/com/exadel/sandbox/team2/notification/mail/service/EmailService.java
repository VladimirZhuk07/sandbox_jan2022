package com.exadel.sandbox.team2.notification.mail.service;

import com.exadel.sandbox.team2.notification.mail.dto.MailDto;
import com.exadel.sandbox.team2.notification.mail.dto.RegistrationMailDto;

public interface EmailService {
    void sendMail(MailDto mailDto);

    void sendAuthorizationMail(RegistrationMailDto registrationMailDto);
}
