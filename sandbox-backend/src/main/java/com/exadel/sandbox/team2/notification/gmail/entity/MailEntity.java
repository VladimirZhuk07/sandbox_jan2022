package com.exadel.sandbox.team2.notification.gmail.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

public class MailEntity {
    @Getter
    private final String username;
    @Getter
    private final String password;
    @Getter
    @Setter
    private String recipientMail;
    @Getter
    @Setter
    private String headerOfMessage = "Team2 Testing Gmail TLS";
    @Getter
    @Setter
    private String textOfMessage = "We love Java!";

    public MailEntity(@Value("${notification.mail.username}") String username,
                      @Value("${notification.mail.password}") String password,
                      String recipientMail,
                      String headerOfMessage,
                      String textOfMessage) {
        this.username = username;
        this.password = password;
        this.recipientMail = recipientMail;
        this.headerOfMessage = headerOfMessage;
        this.textOfMessage = textOfMessage;
    }
}
