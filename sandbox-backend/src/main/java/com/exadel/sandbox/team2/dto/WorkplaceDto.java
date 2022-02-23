package com.exadel.sandbox.team2.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkplaceDto {

    private long workplaceNumber;

    private long mapId;

    private boolean nextToWindow;

    private boolean pc;

    private boolean monitor;

    private boolean keyboard;

    private boolean mouse;

    private boolean headset;
}
