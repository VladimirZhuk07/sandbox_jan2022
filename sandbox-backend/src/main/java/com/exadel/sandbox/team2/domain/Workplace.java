package com.exadel.sandbox.team2.domain;

import com.exadel.sandbox.team2.domain.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Workplace extends BaseEntity {

    private long workplaceNumber;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "mapId")
    private Map mapId;

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
