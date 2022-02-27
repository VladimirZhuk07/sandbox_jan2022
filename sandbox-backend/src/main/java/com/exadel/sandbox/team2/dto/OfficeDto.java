package com.exadel.sandbox.team2.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OfficeDto {

    long id;
    String cityName;
    String name;
    String address;
    boolean hasParking;


}
