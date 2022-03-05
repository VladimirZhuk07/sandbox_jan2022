package com.exadel.sandbox.team2.dto;

import com.exadel.sandbox.team2.domain.base.BaseDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class BookingDto extends BaseDto {

    private long workplaceId;

    private long userId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean recurring = false;

    private Boolean monday = false;

    private Boolean tuesday = false;

    private Boolean wednesday = false;

    private Boolean thursday = false;

    private Boolean friday = false;

    private Boolean saturday = false;

    private Boolean sunday = false;

    private String createdBy;

    private LocalDateTime createdDate;

    private String modifiedBy;

    private LocalDateTime modifiedDate;
}
