package com.exadel.sandbox.team2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity
public class City {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "countryName")
    private Country countryName;
    @Id
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Office> offices;
}
