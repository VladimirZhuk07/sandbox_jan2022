package com.exadel.sandbox.team2.domain;

import com.exadel.sandbox.team2.domain.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table
public class User extends BaseEntity {
    @Column
    private String username;
}
