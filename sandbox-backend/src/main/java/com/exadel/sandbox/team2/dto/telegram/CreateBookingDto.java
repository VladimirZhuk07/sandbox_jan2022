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

    private long workplaceId;

    private long userId;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean recurring = false;

    private Boolean monday = false;

    private Boolean tuesday = false;

    private Boolean wednesday = false;

    private Boolean thursday = false;

    private Boolean friday = false;

    private Boolean saturday = false;

    private Boolean sunday = false;

}
