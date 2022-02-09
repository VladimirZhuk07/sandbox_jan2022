package com.exadel.sandbox.team2.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder

@Entity
public class Country{

    @Id
    @Column(unique = true)
    String name;

}
