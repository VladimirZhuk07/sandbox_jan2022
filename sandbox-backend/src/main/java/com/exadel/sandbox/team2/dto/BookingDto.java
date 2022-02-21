package com.exadel.sandbox.team2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDto {

    private long workplaceId;

    private long userId;

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

    private String createdBy;

    private LocalDateTime createdDate;

    private String modifiedBy;

    private LocalDateTime modifiedDate;
}
