package com.exadel.sandbox.team2.domain;

import com.exadel.sandbox.team2.domain.base.AuditableEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"user", "workplace"}, callSuper = false)
@SuperBuilder

@Entity
public class Booking extends AuditableEntity {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean isRecurring;

    private boolean monday;

    private boolean tuesday;

    private boolean wednesday;

    private boolean thursday;

    private boolean friday;

    private boolean saturday;

    private boolean sunday;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "workplaceId")
    private Workplace workplace;

}
