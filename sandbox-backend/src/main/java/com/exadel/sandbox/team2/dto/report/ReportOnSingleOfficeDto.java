package com.exadel.sandbox.team2.dto.report;

import java.util.Date;

public interface ReportOnSingleOfficeDto {
    String getOfficeName();

    String getCountryName();

    String getCityName();

    String getOfficeAddress();

    Boolean getIsParking();

    Date getCreatedDate();

    Long getNumberOfBooked();
}
