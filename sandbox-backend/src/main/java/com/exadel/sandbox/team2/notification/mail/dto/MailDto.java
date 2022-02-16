package com.exadel.sandbox.team2.notification.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MailDto {
    private String recipientMail;
    private String headerOfMessage;
    private String textOfMessage;
}
