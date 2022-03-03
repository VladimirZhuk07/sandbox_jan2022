package com.exadel.sandbox.team2.dto;

import com.exadel.sandbox.team2.domain.base.BaseDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OfficeDto extends BaseDto {
    long cityId;
    String name;
    String address;
    Boolean parking;
}
