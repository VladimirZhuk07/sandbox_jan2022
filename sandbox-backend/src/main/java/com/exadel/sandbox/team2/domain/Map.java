package com.exadel.sandbox.team2.domain;

import com.exadel.sandbox.team2.domain.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder

@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Map extends BaseEntity{

    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "officeId")
    Office office;
    int floorNum = 1;
    int kitchenNum = 1;
    int confRoomsNum = 1;

    @OneToMany(mappedBy = "map", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Workplace> workplace = new ArrayList<>();

}
