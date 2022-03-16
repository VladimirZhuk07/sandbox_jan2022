package com.exadel.sandbox.team2.dto.report;

import java.util.Date;

public interface ReportOnEmployeesDto {

    String getFirstName();

    String getLastName();

    Date getCreatedDate();

    String getEmail();

    String getOfficeName();

    String getCountryName();

    Long getNumberOfBooked();
}
