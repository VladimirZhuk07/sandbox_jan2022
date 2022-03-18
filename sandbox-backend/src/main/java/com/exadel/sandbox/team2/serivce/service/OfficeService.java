package com.exadel.sandbox.team2.serivce.service;

import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.dto.OfficeDto;
import com.exadel.sandbox.team2.dto.report.ReportOnAllOfficesDto;
import com.exadel.sandbox.team2.dto.report.ReportOnSingleOfficeDto;
import com.exadel.sandbox.team2.serivce.base.BaseDtoService;

import java.util.Date;
import java.util.List;

public interface OfficeService extends BaseDtoService<Office, OfficeDto> {

    void deleteByCity(long id);

    List<Office> findByCityId(long id);

    List<Office> findByCityName(String cityName);

    List<ReportOnSingleOfficeDto> getDataForReportBySingleOffice(Long idOfOffice, Date creationDateFrom, Date creationDateTo);

    List<ReportOnAllOfficesDto> getDataForReportByAllOffices(Date creationDateFrom, Date creationDateTo);
}
