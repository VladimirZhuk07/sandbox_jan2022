package com.exadel.sandbox.team2.domain;

import com.exadel.sandbox.team2.domain.base.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
public class Booking extends AuditableEntity {

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean recurring = false;

    private Boolean monday = false;

    private Boolean tuesday = false;

    private Boolean wednesday = false;

    private Boolean thursday = false;

    private Boolean friday = false;

    private Boolean saturday = false;

    private Boolean sunday = false;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "workplaceId")
    private Workplace workplace;

}
