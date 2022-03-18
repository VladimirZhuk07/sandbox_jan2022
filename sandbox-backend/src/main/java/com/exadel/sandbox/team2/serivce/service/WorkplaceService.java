package com.exadel.sandbox.team2.serivce.service;

import com.exadel.sandbox.team2.domain.Workplace;
import com.exadel.sandbox.team2.dto.WorkplaceDto;
import com.exadel.sandbox.team2.dto.telegram.CreateBookingDto;
import com.exadel.sandbox.team2.serivce.base.BaseDtoService;

import java.time.LocalDate;
import java.util.List;

public interface WorkplaceService extends BaseDtoService<Workplace,WorkplaceDto> {
    List<Workplace> findByMapId(long id);

    List<Workplace> findByMapIdAndNotStartDate(long mapId, CreateBookingDto dto);
}
