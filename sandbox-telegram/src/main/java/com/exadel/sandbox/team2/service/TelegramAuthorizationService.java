package com.exadel.sandbox.team2.service;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.UserState;
import com.exadel.sandbox.team2.serivce.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class TelegramAuthorizationService {

    private final UserService userService;

    public TelegramAuthorizationService(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> findActiveUserByChatId(String chatId){
        return userService.findActiveUserByChatId(chatId);
    }

    public Optional<User> authenticatePhoneNumber(String chatId, String phoneNumber) {
        Optional<User> current = userService.findUserByChatIdOrPhoneNumber(chatId, phoneNumber);
        if(current.isPresent()){
            User user = current.get();
            if (Objects.equals(chatId, user.getChatId()) && user.getStatus() == UserState.WAIT_PHONE_AUTHORIZATION) {
                user.setStatus(UserState.ACTIVE);
                user.setPhoneNumber(phoneNumber);
            } else {
                user.setChatId(chatId);
                user.setStatus(UserState.NEW);
            }
            return Optional.of(userService.save(user));
        }
        return Optional.empty();
    }

    public Optional<User> authenticate(String chatId, String code) {
        Optional<User> current = userService.findInvitedUserByAuthorizationCode(code);
        if(current.isPresent()){
            User user = current.get();
            user.setChatId(chatId);
            user.setStatus(UserState.WAIT_PHONE_AUTHORIZATION);
            return Optional.of(userService.save(user));
        }
        return Optional.empty();
    }
}
