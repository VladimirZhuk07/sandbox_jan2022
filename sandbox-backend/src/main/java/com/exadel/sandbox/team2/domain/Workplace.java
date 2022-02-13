package com.exadel.sandbox.team2.domain;

import com.exadel.sandbox.team2.domain.base.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
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

    private Boolean nextToWindow;

    private Boolean pc;

    private Boolean monitor;

    private Boolean keyboard;

    private Boolean mouse;

    private Boolean headset;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, mappedBy = "workplace")
    private Booking booking;
}
