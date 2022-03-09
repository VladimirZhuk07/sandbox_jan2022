package com.exadel.sandbox.team2.dto;

import com.exadel.sandbox.team2.domain.base.BaseDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class UsersPOJO extends BaseDto {
    String firstName;
    String lastName;
    LocalDate timeStart;
    LocalDate timeEnd;
    long workplaceID;

}
