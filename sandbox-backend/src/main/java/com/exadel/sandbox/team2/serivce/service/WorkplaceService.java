package com.exadel.sandbox.team2.serivce.service;

import com.exadel.sandbox.team2.domain.Workplace;
import com.exadel.sandbox.team2.dto.WorkplaceDto;
import com.exadel.sandbox.team2.serivce.base.CRUDService;

public interface WorkplaceService extends CRUDService<Workplace> {

    WorkplaceDto save(WorkplaceDto workplaceDto);

    WorkplaceDto update(Long id, WorkplaceDto workplaceDto);
}
