package com.exadel.sandbox.team2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkplaceDto {

    private Long workplaceNumber;

    private Boolean nextToWindow;

    private Boolean pc;

    private Boolean monitor;

    private Boolean keyboard;

    private Boolean mouse;

    private Boolean headset;
}
