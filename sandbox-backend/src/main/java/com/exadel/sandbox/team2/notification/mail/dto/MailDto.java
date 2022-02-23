package com.exadel.sandbox.team2.notification.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailDto {
    private String recipient;
    private String header;
    private String text;
}
