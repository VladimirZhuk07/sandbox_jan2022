package com.exadel.sandbox.team2.dto.report;

import java.time.LocalDate;
import java.util.Date;

public interface ReportOnEmployeesDto {
    Long getId();

    Date getCreatedDate();

    String getFirstName();

    String getLastName();

    String getCountryName();

    String getCityName();

    String getOfficeName();

    LocalDate getStartDate();

    LocalDate getEndDate();
}
