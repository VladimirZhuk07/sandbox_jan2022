package com.exadel.sandbox.team2.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OfficeDto {

    long id;
    CountryDto country;
    String city;
    String name;
    String address;
    boolean hasParking;


}
