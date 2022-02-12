package com.exadel.sandbox.team2.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MapDto {

    long id;
    long officeId;
    int floorNum;
    int kitchenNum;
    int confRoomsNum;

}
