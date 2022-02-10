package com.exadel.sandbox.team2.domain;

import com.exadel.sandbox.team2.domain.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"user"}, callSuper = false)
@ToString(exclude = {"user"})
@SuperBuilder

@Entity
@Table(name = "Vacation")
public class Vacation extends BaseEntity {

    @Column(name = "vacation_start")
    private LocalDate vacationStart;

    @Column(name = "vacation_end")
    private LocalDate vacationEnd;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
