package com.exadel.sandbox.team2.domain;

import com.exadel.sandbox.team2.domain.base.AuditableEntity;
import com.exadel.sandbox.team2.domain.enums.Status;
import com.exadel.sandbox.team2.domain.enums.TelegramState;
import com.exadel.sandbox.team2.domain.enums.UserState;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder

@Entity
public class User extends AuditableEntity {

    private String chatId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private LocalDate employmentStart;

    private LocalDate employmentEnd;

    private Boolean isFired;

    private String telegramAuthorizationCode;
  
    @Enumerated(EnumType.STRING)
    @Column
    private TelegramState telegramState;
  
    @Enumerated(EnumType.STRING)
    @Column
    private UserState status;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "UserRole"
            , joinColumns = @JoinColumn(name = "userId")
            , inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private Vacation vacation;

    @Transient
    public static String SYSTEM_USER = "Rony";
}