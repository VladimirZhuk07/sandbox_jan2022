package com.exadel.sandbox.team2.serivce;

import com.exadel.sandbox.team2.domain.Workplace;
import com.exadel.sandbox.team2.dto.WorkplaceDto;

public interface WorkplaceService extends CRUDService<Workplace> {

    WorkplaceDto save(WorkplaceDto workplaceDto);

    WorkplaceDto update(Long id, WorkplaceDto workplaceDto);
}
