package com.exadel.sandbox.team2.dto;

import com.exadel.sandbox.team2.domain.enums.Status;
import com.exadel.sandbox.team2.domain.enums.TelegramState;
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

    private TelegramState telegramState;

    private Status status;

    private String createdBy;

    private LocalDateTime createdDate;

    private String modifiedBy;

    private LocalDateTime modifiedDate;
}
