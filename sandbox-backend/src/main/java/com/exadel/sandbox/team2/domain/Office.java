package com.exadel.sandbox.team2.domain;

import com.exadel.sandbox.team2.domain.base.AuditableEntity;
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "countryName")
    Country countryName;
    @OneToOne(mappedBy = "officeId",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Map map;
    String city;
    String name;
    String address;
    boolean hasParking;

}
