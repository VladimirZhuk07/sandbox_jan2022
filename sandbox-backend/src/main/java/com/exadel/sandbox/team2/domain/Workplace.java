package com.exadel.sandbox.team2.domain;

import com.exadel.sandbox.team2.domain.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
public class Workplace extends BaseEntity {

    private Long workplaceNumber;

    private boolean nextToWindow;

    private boolean pc;

    private boolean monitor;

    private boolean keyboard;

    private boolean mouse;

    private boolean headset;

    @OneToOne(mappedBy = "workplaceId", cascade = CascadeType.ALL)
    @JsonIgnore
    private Booking bookingId;
}
