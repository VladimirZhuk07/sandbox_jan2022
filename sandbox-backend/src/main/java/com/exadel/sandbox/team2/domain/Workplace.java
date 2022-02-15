package com.exadel.sandbox.team2.domain;

import com.exadel.sandbox.team2.domain.base.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
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

    @OneToOne(mappedBy = "workplace")
    private Booking booking;
}
