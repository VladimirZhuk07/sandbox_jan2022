package com.exadel.sandbox.team2.notification.mail.service.impl;

import com.exadel.sandbox.team2.notification.mail.configuration.MailSettingProperties;
import com.exadel.sandbox.team2.notification.mail.dto.MailDto;
import com.exadel.sandbox.team2.notification.mail.service.EmailService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    MailDto mailDto;

    @Setter
    @Getter
    private Boolean emailNotificationEnabled = false;

    private final MailSettingProperties mailProperties;

    @SneakyThrows
    public void sendMail() {
        log.info("Calling method sendMail in Class EmailService with emailNotificationEnabled parameter equal to {}.", emailNotificationEnabled);
        if (emailNotificationEnabled) {
            String recipient = mailDto.getRecipientMail();
            Properties properties = setSettingsOfProperties();

            Session session = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(mailProperties.getUsernameOfMailFromSend(),
                                    mailProperties.getPasswordOfMailFormSend());
                        }
                    });

            Message message = prepareMessage(session, recipient);
            Transport.send(message);
            log.info("Successfully send to recipient.");
        }
    }

    public Message prepareMessage(Session session, String recipient) {
        Message message = null;
        try {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailProperties.getUsernameOfMailFromSend()));
            message.setRecipients(
                    Message.RecipientType.TO,
                    new InternetAddress[]{new InternetAddress(recipient)}
            );

            message.setSubject(mailDto.getHeaderOfMessage());

            message.setText(mailDto.getTextOfMessage());
            log.info("Email to: {}, with title: {}, and text: {}", recipient, mailDto.getHeaderOfMessage(), mailDto.getTextOfMessage());
        } catch (MessagingException e) {
            log.error("Error in EmailService class, method  prepareMessage: " + e.getMessage());
        }
        return message;
    }

    public Properties setSettingsOfProperties() {
        Properties properties = new Properties();
        properties.put(mailProperties.getHostName(), mailProperties.getHostValue());
        properties.put(mailProperties.getPortName(), mailProperties.getPortValue());
        properties.put(mailProperties.getAuthName(), mailProperties.getAuthValue());
        properties.put(mailProperties.getStarttlsName(), mailProperties.getStarttlsValue());
        return properties;
    }
}
