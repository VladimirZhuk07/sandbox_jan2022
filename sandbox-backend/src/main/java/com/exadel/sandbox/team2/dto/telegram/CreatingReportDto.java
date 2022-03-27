package com.exadel.sandbox.team2.dto.telegram;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatingReportDto {
    private Long id;
    private Date bookingFrom;
    private Date bookingTo;
}
