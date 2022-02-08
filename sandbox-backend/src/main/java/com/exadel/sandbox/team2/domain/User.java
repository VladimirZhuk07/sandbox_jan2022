package com.exadel.sandbox.team2.domain;

import com.exadel.sandbox.team2.domain.base.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class User extends BaseEntity {
}