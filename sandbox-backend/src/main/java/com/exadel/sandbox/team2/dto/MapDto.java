package com.exadel.sandbox.team2.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MapDto {

    long id;
    OfficeDto office;
    int floorNum;
    int kitchenNum;
    int confRoomsNum;

}
