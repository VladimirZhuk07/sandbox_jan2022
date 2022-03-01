package com.exadel.sandbox.team2.domain;

import com.exadel.sandbox.team2.domain.base.AuditableEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
public class Booking extends AuditableEntity {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private boolean recurring;

    private boolean monday;

    private boolean tuesday;

    private boolean wednesday;

    private boolean thursday;

    private boolean friday;

    private boolean saturday;

    private boolean sunday;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "userId")
    private User userId;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "workplaceId")
    private Workplace workplaceId;

}
