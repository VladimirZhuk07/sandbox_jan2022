package com.exadel.sandbox.team2.domain;

import com.exadel.sandbox.team2.domain.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
public class Vacation extends BaseEntity {

    private LocalDate vacationStart;

    private LocalDate vacationEnd;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "userId")
    private User userId;
}
