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

    @Value("notification.mail.username")
    private String usernameOfMailFromSend;

    @Value("notification.mail.password")
    private String passwordOfMailFormSend;

    private String hostName = "mail.smtp.host";

    @Value("notification.properties.host")
    private String hostValue;

    private String portName = "mail.smtp.port";

    @Value("notification.properties.port")

    private String portValue;

    private String authName = "mail.smtp.auth";

    @Value("notification.properties.auth")
    private String authValue;

    private String starttlsName = "mail.smtp.starttls.enable";

    @Value("notification.properties.starttls")
    private String starttlsValue;

}
