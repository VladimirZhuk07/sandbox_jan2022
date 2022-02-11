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

    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean isRecurring;

    private String frequency;

    private String createdBy;

    private LocalDateTime createdDate;

    private String modifiedBy;

    private LocalDateTime modifiedDate;
}
