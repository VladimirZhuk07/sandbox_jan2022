package com.exadel.sandbox.team2.telegram.scheduler.impl;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.UserState;
import com.exadel.sandbox.team2.notification.mail.dto.MailDto;
import com.exadel.sandbox.team2.notification.mail.service.impl.EmailServiceImpl;
import com.exadel.sandbox.team2.telegram.configuration.TelegramProperties;
import com.exadel.sandbox.team2.telegram.scheduler.ReminderRegistration;
import com.exadel.sandbox.team2.serivce.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class ReminderRegistrationByEmailImpl implements ReminderRegistration {

    private final UserServiceImpl userService;
    private final EmailServiceImpl emailService;
    private final TelegramProperties telegramProperties;

    @Override
    @Scheduled(fixedDelay = 30000)
    public void remindToNewUsers() {
        List<User> newUsers = userService.findAllByStatus(UserState.NEW);
        MailDto userInfo;
        for (User user : newUsers) {
            userInfo = new MailDto();
            System.out.println(user.getEmail());
            userInfo.setRecipient(user.getEmail());
            user.getTelegramAuthorizationCode();
            userInfo.setHeader("Dear " + user.getFirstName() + ", please complete registration in our system");
            emailService.sendMail(userInfo);
        }
    }

    @Scheduled(fixedDelay = 30000)
    public void sendAuthorizationLink(String chatId) {
        MailDto mailDto = new MailDto();
        String code = UUID.randomUUID().toString();
        mailDto.setLink(code);
        mailDto.putTextAndLink(telegramProperties.getBot().getUsername());

        User user = userService.getUserByTelegramChatIdOrPhone(chatId, "1").get();
        user.setTelegramAuthorizationCode(code);
        user.setStatus(UserState.NEW);
        userService.save(user);
        emailService.sendMail(mailDto);
    }
}
