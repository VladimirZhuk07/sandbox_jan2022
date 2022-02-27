package com.exadel.sandbox.team2.scheduler.impl;

import com.exadel.sandbox.team2.configuration.TelegramProperties;
import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.UserState;
import com.exadel.sandbox.team2.dto.MailDto;
import com.exadel.sandbox.team2.scheduler.ReminderRegistration;
import com.exadel.sandbox.team2.service.EmailService;
import com.exadel.sandbox.team2.serivce.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ReminderRegistrationByEmailImpl implements ReminderRegistration {

    private final UserServiceImpl userService;
    private final EmailService emailService;
    private final TelegramProperties telegramProperties;

    @Override
    @Scheduled(fixedDelay = 30000)
    public void remindToNewUsers() {
        List<User> newUsers = userService.findAllByStatus(UserState.NEW);
        newUsers.forEach(user -> {
            MailDto userInfo = new MailDto();
            userInfo.setRecipient(user.getEmail());
            userInfo.setLink(user.getTelegramAuthorizationCode());
            userInfo.putTextAndLink(telegramProperties.getBot().getUsername());
            userInfo.setHeader("Dear " + user.getFirstName() + ", please complete registration in our system");
            emailService.sendMail(userInfo);
            user.setStatus(UserState.INVITED);
            userService.save(user);
        });
    }
}
