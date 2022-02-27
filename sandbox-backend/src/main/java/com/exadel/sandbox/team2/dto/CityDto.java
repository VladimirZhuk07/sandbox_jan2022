package com.exadel.sandbox.team2.dto;

import com.exadel.sandbox.team2.domain.Country;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class CityDto {
    private Country countryName;
    private String name;
}
