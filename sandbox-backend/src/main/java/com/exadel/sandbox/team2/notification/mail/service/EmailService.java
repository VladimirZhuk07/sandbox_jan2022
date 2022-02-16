package com.exadel.sandbox.team2.notification.mail.service;

import javax.mail.Message;
import javax.mail.Session;
import java.util.Properties;

public interface EmailService {
    void sendMail();
    Message prepareMessage(Session session, String recipient);
    Properties setSettingsOfProperties();
}
