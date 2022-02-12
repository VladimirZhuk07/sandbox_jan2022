package com.exadel.sandbox.team2.dto;

import com.exadel.sandbox.team2.domain.Country;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OfficeDto {

    long id;
    CountryDto countryName;
    String city;
    String name;
    String address;
    boolean hasParking;


}
