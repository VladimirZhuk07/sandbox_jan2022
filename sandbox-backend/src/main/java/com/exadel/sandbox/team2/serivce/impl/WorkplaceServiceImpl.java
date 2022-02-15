package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.WorkplaceRepository;
import com.exadel.sandbox.team2.domain.Workplace;
import com.exadel.sandbox.team2.dto.WorkplaceDto;
import com.exadel.sandbox.team2.mapper.WorkplaceMapper;
import com.exadel.sandbox.team2.serivce.WorkplaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkplaceServiceImpl extends CRUDServiceImpl<Workplace> implements WorkplaceService {

    private final WorkplaceRepository workplaceRepository;

    private final WorkplaceMapper mapper;

    @Override
    public WorkplaceDto save(WorkplaceDto workplaceDto) {
        Workplace workplace = mapper.toEntity(workplaceDto);
        Workplace newWorkplace = workplaceRepository.save(workplace);
        return mapper.toDto(newWorkplace);
    }

    @Override
    public WorkplaceDto update(Long id, WorkplaceDto workplaceDto) {
        Workplace newWorkplace = mapper.toEntity(workplaceDto);
        newWorkplace.setId(id);
        workplaceRepository.save(newWorkplace);
        return mapper.toDto(newWorkplace);
    }
}
