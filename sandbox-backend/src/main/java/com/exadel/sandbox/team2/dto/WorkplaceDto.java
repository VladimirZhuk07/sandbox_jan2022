package com.exadel.sandbox.team2.dto;

import com.exadel.sandbox.team2.domain.base.BaseDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class WorkplaceDto extends BaseDto {

    private long workplaceNumber;

    private long mapId;

    private Integer floor;

    private Boolean nextToWindow = false;

    private Boolean pc = false;

    private Boolean monitor = false;

    private Boolean keyboard = false;

    private Boolean mouse = false;

    private Boolean headset = false;
}
