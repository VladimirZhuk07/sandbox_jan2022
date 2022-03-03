package com.exadel.sandbox.team2.dto;

import com.exadel.sandbox.team2.domain.base.BaseDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class BookingDto extends BaseDto {

    private long workplaceId;

    private long userId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean recurring;

    private Boolean monday;

    private Boolean tuesday;

    private Boolean wednesday;

    private Boolean thursday;

    private Boolean friday;

    private Boolean saturday;

    private Boolean sunday;

    private String createdBy;

    private LocalDateTime createdDate;

    private String modifiedBy;

    private LocalDateTime modifiedDate;
}
