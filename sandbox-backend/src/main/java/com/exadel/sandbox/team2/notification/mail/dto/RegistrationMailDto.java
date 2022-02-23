package com.exadel.sandbox.team2.notification.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationMailDto {
    private String recipientMail;
    private String headerOfMessage = "Verify your account";
    private String textOfMessage;
    private String link;

    {
        this.textOfMessage = String.format(
                """
                        Hello!\s
                        This email was sent to verify your account
                        Please follow the link below to verify it:
                        http://localhost:8080/api/mails/activate/%s""",
                this.link
        );
    }
}
