package com.exadel.sandbox.team2.dto.telegramDto;

import com.exadel.sandbox.team2.domain.enums.TelegramState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingDto {

    private String cityName;

    private String countryName;

    private String officeName;

    private String officeAddress;

    private Boolean nextToWindow;

    private Boolean pc;

    private Boolean monitor;

    private Boolean keyboard;

    private Boolean mouse;

    private Boolean headset;

    private Integer kitchenNum;

    private Integer confRoomsNum;

    private long workplaceId;

    private long userId;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean recurring = false;

    private Boolean monday;

    private Boolean tuesday;

    private Boolean wednesday;

    private Boolean thursday;

    private Boolean friday;

    private Boolean saturday;

    private Boolean sunday;

    private TelegramState bookingTypeBeforeDefineWorkplaceAttributes;

    private int weekTimes;
}
