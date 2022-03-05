package com.exadel.sandbox.team2.dto;

import com.exadel.sandbox.team2.domain.base.BaseDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MapDto extends BaseDto {
    long officeId;
    int floorNum;
    int kitchenNum;
    int confRoomsNum;
}
