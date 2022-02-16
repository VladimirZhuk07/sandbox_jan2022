package com.exadel.sandbox.team2.notification.mail.dto;

import lombok.Getter;

@Getter
public class MailDto {
    private String recipientMail;
    private String headerOfMessage;
    private String textOfMessage;

    public MailDto(String recipientMail, String headerOfMessage, String textOfMessage) {
        this.recipientMail = recipientMail;
        this.headerOfMessage = headerOfMessage;
        this.textOfMessage = textOfMessage;
    }
}
