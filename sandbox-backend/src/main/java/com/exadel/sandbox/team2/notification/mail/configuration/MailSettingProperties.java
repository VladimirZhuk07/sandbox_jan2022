package com.exadel.sandbox.team2.notification.mail.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
public class MailSettingProperties {
    public String hostName = "mail.smtp.host";
    private String portName = "mail.smtp.port";
    private String authName = "mail.smtp.auth";
    private String starttlsName = "mail.smtp.starttls.enable";

    @Value("notification.mail.username")
    private String usernameOfMailFromSend;

    @Value("notification.mail.password")
    private String passwordOfMailFormSend;

    @Value("notification.mail.host")
    private String hostValue;

    @Value("notification.mail.port")
    private String portValue;

    @Value("notification.mail.auth")
    private String authValue;

    @Value("notification.mail.starttls")
    private String starttlsValue;

}
