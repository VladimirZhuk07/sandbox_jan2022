package com.exadel.sandbox.team2.notification.gmail.service;

import com.exadel.sandbox.team2.notification.gmail.configuration.MailSettingProperties;
import com.exadel.sandbox.team2.notification.gmail.entity.MailEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {
    @Autowired
    MailEntity mailEntity;
    @Setter
    @Getter
    private Boolean emailNotificationEnabled = false;
    private final MailSettingProperties mailSettingProperties;

    @SneakyThrows
    public void sendMail() {
        log.info("Calling method sendMail in Class EmailService with emailNotificationEnabled parameter equal to {}.", emailNotificationEnabled);
        if (emailNotificationEnabled) {
            String recipient = mailEntity.getRecipientMail();
            Properties properties = setSettingsOfProperties();

            Session session = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(mailEntity.getUsername(), mailEntity.getPassword());
                        }
                    });

            Message message = prepareMessage(session, recipient);
            Transport.send(message);
            log.info("Successfully send to recipient.");
        }
    }

    private Message prepareMessage(Session session, String recipient) {
        Message message = null;
        try {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailEntity.getUsername()));
            message.setRecipients(
                    Message.RecipientType.TO,
                    new InternetAddress[]{new InternetAddress(recipient)}
            );

            message.setSubject(mailEntity.getHeaderOfMessage());
            message.setText(mailEntity.getTextOfMessage());
            log.info("Email to: {}, with title: {}, and text: {}", recipient, mailEntity.getHeaderOfMessage(), mailEntity.getTextOfMessage());
        } catch (MessagingException e) {
            log.error("Error in EmailService class, method  prepareMessage: " + e.getMessage());
        }
        return message;
    }

    private Properties setSettingsOfProperties() {
        Properties properties = new Properties();
        properties.put(mailSettingProperties.getProperties().getHost().getName(), mailSettingProperties.getProperties().getHost().getValue());
        properties.put(mailSettingProperties.getProperties().getPort().getName(), mailSettingProperties.getProperties().getPort().getValue());
        properties.put(mailSettingProperties.getProperties().getAuth().getName(), mailSettingProperties.getProperties().getAuth().getValue());
        properties.put(mailSettingProperties.getProperties().getStarttls().getName(), mailSettingProperties.getProperties().getStarttls().getValue());
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.port", "587");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
        return properties;
    }
}
