package com.exadel.sandbox.team2.telegram.service;

import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.UserState;
import com.exadel.sandbox.team2.serivce.service.UserService;
import com.exadel.sandbox.team2.telegram.dto.TelegramResponse;
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

    public TelegramResponse<?> saveTelegramUserPhone(String chatId, String phoneNumber) {
        Optional<User> optionalUser = userService.getUserByTelegramChatIdOrPhone(chatId, phoneNumber);
        if (optionalUser.isEmpty()) {
            return TelegramResponse.builder()
                    .code(404)
                    .message("User not found")
                    .build();
        }

        User user = optionalUser.get();

        if (Objects.equals(chatId, user.getChatId()) && user.getStatus() == UserState.WAIT_PHONE_AUTHORIZATION) {
            user.setStatus(UserState.ACTIVE);
            user.setPhoneNumber(phoneNumber);
            userService.save(user);
            return TelegramResponse.builder()
                    .code(200)
                    .message("Successfully user activated")
                    .build();
        } else if (Objects.equals(phoneNumber, user.getPhoneNumber())) {
            user.setChatId(chatId);
            user.setStatus(UserState.NEW);
            userService.save(user);
            return TelegramResponse.builder()
                    .code(201)
                    .message("Please wait invitation via email")
                    .build();
        }
        return TelegramResponse.builder()
                .code(500)
                .message("Some error")
                .build();

    }

    public TelegramResponse<?> authorizationTelegramUser(String code, String chatId) {
        Optional<User> optionalUser = userService.getUserByAuthorizationCode(code);
        if (optionalUser.isEmpty()) {
            return TelegramResponse.builder()
                    .code(404)
                    .message("User not found")
                    .build();
        }

        User user = optionalUser.get();

        if (user.getStatus() != UserState.INVITED) {
            return TelegramResponse.builder()
                    .code(405)
                    .message("Expired authorization code")
                    .build();
        }

        user.setChatId(chatId);
        user.setStatus(UserState.WAIT_PHONE_AUTHORIZATION);
        userService.save(user);

        return TelegramResponse.builder()
                .code(200)
                .message("User successfully updated")
                .build();
    }


}
