package com.exadel.sandbox.team2.dto;

import com.exadel.sandbox.team2.domain.enums.TelegramState;
import com.exadel.sandbox.team2.domain.enums.UserState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String chatId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private LocalDate employmentStart;

    private LocalDate employmentEnd;

    private Boolean isFired;

    private String telegramAuthorizationCode;

    private String password;

    private TelegramState telegramState;

    private UserState status;

    private String createdBy;

    private LocalDateTime createdDate;

    private String modifiedBy;

    private LocalDateTime modifiedDate;
}
