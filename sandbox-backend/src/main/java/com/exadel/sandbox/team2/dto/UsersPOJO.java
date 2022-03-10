package com.exadel.sandbox.team2.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersPOJO {
    Long id;
    String firstName;
    String lastName;
    LocalDate timeStart;
    LocalDate timeEnd;
    Long workplaceID;
}
