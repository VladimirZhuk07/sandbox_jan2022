package com.exadel.sandbox.team2.domain;

import com.exadel.sandbox.team2.domain.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private Map map;

    private Boolean nextToWindow;

    private Boolean pc;

    private Boolean monitor;

    private Boolean keyboard;

    private Boolean mouse;

    private Boolean headset;

    @OneToMany( cascade = CascadeType.ALL, mappedBy = "workplace",orphanRemoval = true)
    @JsonIgnore
    private List<Booking> bookings = new ArrayList<>();
}
