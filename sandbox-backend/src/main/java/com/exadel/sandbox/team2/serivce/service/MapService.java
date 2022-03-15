package com.exadel.sandbox.team2.serivce.service;

import com.exadel.sandbox.team2.domain.Map;
import com.exadel.sandbox.team2.dto.MapDto;
import com.exadel.sandbox.team2.dto.report.ReportOnAllOfficesDto;
import com.exadel.sandbox.team2.dto.report.ReportOnFloorDto;
import com.exadel.sandbox.team2.serivce.base.BaseDtoService;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MapService extends BaseDtoService<Map,MapDto> {

    void deleteByOfficeId(long officeId);

    Map findByOfficeId(Long id);

    List<ReportOnFloorDto> getDataForReportOnFloor(Long idOfFloor, Date bookedDateFrom, Date bookedDateTo);
}
