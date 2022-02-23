package com.exadel.sandbox.team2.notification.mail.service;

import com.exadel.sandbox.team2.dao.UserRepository;
import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.notification.mail.dto.MailDto;
import com.exadel.sandbox.team2.notification.mail.dto.RegistrationMailDto;
import com.exadel.sandbox.team2.notification.mail.service.impl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSender {
    private final EmailServiceImpl emailService;
    private final UserRepository repository;

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
    public void sendAuthorizationMail(long id) {
        try {
            RegistrationMailDto registrationMailDto = new RegistrationMailDto();
            User user = repository.getOne(id);
            registrationMailDto.setRecipientMail(user.getEmail());
            registrationMailDto.setLink(user.getTelegramAuthorizationCode());
            log.info("SendAuthorizationMail method called.");
            emailService.sendAuthorizationMail(registrationMailDto);
        } catch (Exception e) {
            log.error("Error during sending of authorization email.", e);
        }

    }

    @Async
    public void verifyTelegramAuthorizationCode(String telegramAuthorizationCode) {
        User user = repository.findByTelegramAuthorizationCode(telegramAuthorizationCode).get();
        user.setStatus(UserState.ACTIVE);
        user.setTelegramAuthorizationCode(null);
        this.repository.save(user);
    }
}
