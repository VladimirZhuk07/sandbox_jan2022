package com.exadel.sandbox.team2.notification.mail.service.impl;

import com.exadel.sandbox.team2.notification.mail.configuration.MailSettingProperties;
import com.exadel.sandbox.team2.notification.mail.dto.MailDto;
import com.exadel.sandbox.team2.notification.mail.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
    private final MailSettingProperties mailProperties;

    @SneakyThrows
    public void sendMail(MailDto mailDto) {
        String recipient = mailDto.getRecipientMail();
        Properties properties = setSettingsOfProperties();

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailProperties.getUsernameOfMailFromSend(),
                        mailProperties.getPasswordOfMailFormSend());
            }
        };
        Session session = Session.getInstance(properties,auth);

        Message message = prepareMessage(session, recipient, mailDto);
        Transport.send(message);
        log.info("Successfully send to recipient.");
    }

    private Message prepareMessage(Session session, String recipient, MailDto mailDto) {
        Message message = null;
        session.setDebug(true);
        try {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailProperties.getUsernameOfMailFromSend()));
            message.setRecipients(
                    Message.RecipientType.TO,
                    new InternetAddress[]{new InternetAddress(recipient)}
            );
            message.setSubject(mailDto.getHeaderOfMessage());
            generateMailBody(message);

            log.info("Email to: {}, with title: {}", recipient, mailDto.getHeaderOfMessage());
        } catch (MessagingException e) {
            log.error("Error by preparing message. ", e);
        }
        return message;
    }

    private void generateMailBody(Message message){
        String link = UUID.randomUUID().toString();
        try {
            message.setText(String.format(
                    """
                            Hello!\s
                            This email was sent to verify your account
                            Please follow the link below to verify it:
                            http://localhost:8080/api/mails/activate/%s""",
                    link
            ));

        } catch (MessagingException e) {
            log.error("Error in generating body of message. ", e);
        }
    }

    private Properties setSettingsOfProperties() {
        Properties properties = new Properties();
        properties.put(mailProperties.getHostName(), mailProperties.getHostValue());
        properties.put(mailProperties.getPortName(), mailProperties.getPortValue());
        properties.put(mailProperties.getAuthName(), mailProperties.getAuthValue());
        properties.put(mailProperties.getStarttlsName(), mailProperties.getStarttlsValue());
        return properties;
    }
}
