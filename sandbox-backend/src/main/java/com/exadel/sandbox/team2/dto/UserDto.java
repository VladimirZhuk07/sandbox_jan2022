package com.exadel.sandbox.team2.dto;

import com.exadel.sandbox.team2.domain.base.BaseDto;
import com.exadel.sandbox.team2.domain.enums.TelegramState;
import com.exadel.sandbox.team2.domain.enums.UserState;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class UserDto extends BaseDto {

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
