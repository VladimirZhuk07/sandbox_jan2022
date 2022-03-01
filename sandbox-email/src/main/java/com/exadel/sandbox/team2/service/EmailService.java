package com.exadel.sandbox.team2.service;

import com.exadel.sandbox.team2.dto.MailDto;

public interface EmailService {
    void sendMail(MailDto mailDto);
}
