package com.exadel.sandbox.team2.notification.mail.service.impl;

import com.exadel.sandbox.team2.notification.mail.configuration.MailSettingProperties;
import com.exadel.sandbox.team2.notification.mail.dto.MailDto;
import org.junit.Ignore;
import org.junit.Test;


@Ignore
public class EmailServiceImplTest {

    @Test
    public void sendMail() {
        MailDto message = new MailDto("mirzakolonovazizbek@gmail.com", "Aziz", "Hello World");
        MailSettingProperties mailProperties = new MailSettingProperties();
        System.out.println(mailProperties.getAuthName());
        System.out.println(mailProperties.getAuthValue());
        //EmailServiceImpl emailService = new EmailServiceImpl(mailProperties);
        // emailService.sendMail(message);
    }
}