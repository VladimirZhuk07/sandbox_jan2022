package com.exadel.sandbox.team2.notification.gmail;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Profile("application.yml")
public class SendEmailTLS extends Thread {
    @Value("${notificationGmail.username}")
    private String username = "bookingServiceExadelTeam2@gmail.com";
    @Value("${notificationGmail.password}")
    private String password = "AzizVladTeam2";
    private String recipientMail;
    private String headerOfMessage = "Team2 Testing Gmail TLS";
    private String textOfMessage = "We love Java!";

    public SendEmailTLS() {
    }

    public SendEmailTLS(String recipientMail, String headerOfMessage, String textOfMessage) {
        this.recipientMail = recipientMail;
        this.headerOfMessage = headerOfMessage;
        this.textOfMessage = textOfMessage;
    }

    public String getRecipientMail() {
        return recipientMail;
    }

    public String getHeaderOfMessage() {
        return headerOfMessage;
    }

    public void setHeaderOfMessage(String headerOfMessage) {
        this.headerOfMessage = headerOfMessage;
    }

    public String getTextOfMessage() {
        return textOfMessage;
    }

    public void setTextOfMessage(String textOfMessage) {
        this.textOfMessage = textOfMessage;
    }

    public void setRecipientMail(String recipientMail) {
        this.recipientMail = recipientMail;
    }

    @Override
    public void run() {
        sendMail("mirzakolonovazizbek@gmail.com");
    }

    @SneakyThrows
    private void sendMail(String recipient) {
        recipient = this.recipientMail;
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        Message message = prepareMessage(session, recipient);
        Transport.send(message);
        System.out.println("Done");
    }

    private Message prepareMessage(Session session, String recipient) {
        Message message = null;
        try {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    new InternetAddress[]{new InternetAddress(recipient)}
            );
            message.setSubject(headerOfMessage);

            message.setText(textOfMessage);


        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }
}
