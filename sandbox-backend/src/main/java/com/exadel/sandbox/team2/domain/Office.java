package com.exadel.sandbox.team2.domain;

import com.exadel.sandbox.team2.domain.base.AuditableEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder

@Entity
public class Office extends AuditableEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cityId")
    City city;
    @OneToOne(mappedBy = "office",cascade = CascadeType.ALL)
    @JsonIgnore
    Map map;
    String name;
    String address;
    Boolean parking;

    @Override
    public String toString() {
        return "Office{" +
                "cityId=" + city +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", hasParking=" + parking +
                '}';
    }
}
