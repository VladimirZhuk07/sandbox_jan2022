package com.exadel.sandbox.team2.domain.base;

import lombok.*;

import javax.persistence.*;


@Getter

@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

}
