package com.exadel.sandbox.team2.dto.report;

import java.time.LocalDate;
import java.util.Date;

public interface ReportByEmployeesDto {
    Long getId();

    Date getCreatedDate();

    String getFirstName();

    String getLastName();

    LocalDate getStartDate();

    LocalDate getEndDate();
}
