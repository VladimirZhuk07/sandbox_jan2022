package com.exadel.sandbox.team2.domain;

import com.exadel.sandbox.team2.domain.base.BaseEntity;
import com.exadel.sandbox.team2.domain.enums.RoleType;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "Role")
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column
    private RoleType name;

}
