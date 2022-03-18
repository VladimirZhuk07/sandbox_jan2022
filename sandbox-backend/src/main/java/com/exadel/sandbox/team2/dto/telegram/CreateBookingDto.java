package com.exadel.sandbox.team2.dto.telegram;

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

    private int weekTimes;
}
