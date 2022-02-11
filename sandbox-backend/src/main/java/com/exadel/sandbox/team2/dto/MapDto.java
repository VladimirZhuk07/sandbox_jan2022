package com.exadel.sandbox.team2.dto;

import com.exadel.sandbox.team2.domain.Office;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MapDto {

    long id;
    OfficeDto officeId;
    int floorNum;
    int kitchenNum;
    int confRoomsNum;

}
