package com.exadel.sandbox.team2.domain.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class BaseDto {
    protected long id;
}
