package com.exadel.sandbox.team2.domain;

import com.exadel.sandbox.team2.domain.base.AuditableEntity;
import com.exadel.sandbox.team2.domain.enums.Status;
import com.exadel.sandbox.team2.domain.enums.TelegramState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class User extends AuditableEntity {

    private String chatId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private LocalDate employmentStart;

    private LocalDate employmentEnd;

    private boolean isFired;

    private String telegramAuthorizationCode;

    @Enumerated(EnumType.STRING)
    private TelegramState telegramState;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "UserRole"
            , joinColumns = @JoinColumn(name = "userId")
            , inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "userId")
    @JsonIgnore
    private Vacation vacationId;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "userId")
    @JsonIgnore
    private Booking bookingId;

    @Transient
    public static String SYSTEM_USER = "Rony";
}