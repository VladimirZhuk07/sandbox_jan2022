package com.exadel.sandbox.team2.dto;

import com.exadel.sandbox.team2.domain.base.BaseDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Builder
public class CityDto extends BaseDto {
    private long countryId;
    private String name;
}