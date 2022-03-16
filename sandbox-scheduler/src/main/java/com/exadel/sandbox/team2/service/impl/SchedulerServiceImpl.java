package com.exadel.sandbox.team2.service.impl;

import com.exadel.sandbox.team2.configuration.TelegramProperties;
import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.UserState;
import com.exadel.sandbox.team2.dto.MailDto;
import com.exadel.sandbox.team2.serivce.impl.UserServiceImpl;
import com.exadel.sandbox.team2.service.EmailService;
import com.exadel.sandbox.team2.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {

    private final UserServiceImpl userService;
    private final EmailService emailService;
    private final TelegramProperties telegramProperties;

    @Override
    @Scheduled(fixedDelay = 30000)
    @Async
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

    @Override
    @Scheduled(fixedDelay = 100000)
    @Async
    public void cancelBookings() {
        List<User> list = userService.findByIsFiredTrue();
        for(User user: list){
            MailDto mail = new MailDto();
            mail.setHeader("Your bookings were canceled since");
            mail.setRecipient(user.getEmail());
            mail.setBody("Dear" + user.getFirstName() + "\nYour bookings were canceled, since you was fired from the company");
            emailService.sendMail(mail);
            user.getBookings().clear();
            user.setBookings(new ArrayList<>());
            user.setStatus(UserState.BLOCKED);
            userService.save(user);
        }
    }
}
