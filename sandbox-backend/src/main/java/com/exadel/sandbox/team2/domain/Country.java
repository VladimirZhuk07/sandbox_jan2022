package com.exadel.sandbox.team2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder

@Entity
public class Country{

    @Id
    @Column(unique = true)
    String name;

    @OneToMany(mappedBy = "countryName", cascade = {CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH}, orphanRemoval = true)
    @JsonIgnore
    List<Office> offices;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ")";
    }
}
