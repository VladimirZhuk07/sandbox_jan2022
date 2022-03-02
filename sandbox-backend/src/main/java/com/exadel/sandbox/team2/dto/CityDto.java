package com.exadel.sandbox.team2.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class CityDto {
    private long id;
    private long countryId;
    private String name;
}