package com.exadel.sandbox.team2.notification.mail.service;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.UserState;
import com.exadel.sandbox.team2.notification.mail.dto.MailDto;
import com.exadel.sandbox.team2.notification.mail.service.impl.EmailServiceImpl;
import com.exadel.sandbox.team2.serivce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSender {
    private final EmailServiceImpl emailService;

    private final UserService service;

    @Async
    public void send(MailDto message) {
        try {
            log.info("Send email called.");
            emailService.sendMail(message);
        } catch (Exception e) {
            log.error("Error during sending of email.", e);
        }
    }

    @Async
    public void verifyTelegramAuthorizationCode(String telegramAuthorizationCode) {
        User user = service.getUserByAuthorizationCode(telegramAuthorizationCode).get();
        user.setStatus(UserState.ACTIVE);
        user.setTelegramAuthorizationCode(null);
        user.setStatus(UserState.ACTIVE);
        this.service.save(user);
    }
}
