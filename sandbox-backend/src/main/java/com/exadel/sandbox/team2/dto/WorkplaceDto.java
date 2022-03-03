package com.exadel.sandbox.team2.dto;

import com.exadel.sandbox.team2.domain.base.BaseDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class WorkplaceDto extends BaseDto {

    private long workplaceNumber;

    private long mapId;

    private Boolean nextToWindow;

    private Boolean pc;

    private Boolean monitor;

    private Boolean keyboard;

    private Boolean mouse;

    private Boolean headset;
}
