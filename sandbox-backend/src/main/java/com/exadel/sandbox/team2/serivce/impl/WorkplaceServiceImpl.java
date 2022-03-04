package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.MapRepository;
import com.exadel.sandbox.team2.dao.WorkplaceRepository;
import com.exadel.sandbox.team2.domain.Map;
import com.exadel.sandbox.team2.domain.Workplace;
import com.exadel.sandbox.team2.dto.WorkplaceDto;
import com.exadel.sandbox.team2.mapper.WorkplaceMapper;
import com.exadel.sandbox.team2.serivce.base.CRUDServiceImpl;
import com.exadel.sandbox.team2.serivce.service.WorkplaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkplaceServiceImpl extends CRUDServiceImpl<Workplace,WorkplaceDto> implements WorkplaceService {

    private final WorkplaceRepository repository;
    private final MapRepository mapRepository;
    private final WorkplaceMapper mapper;

    @Override
    public WorkplaceDto save(WorkplaceDto dto) {
        Workplace workplace = mapper.toEntity(dto);
        Map map = mapRepository.getById(dto.getMapId());
        workplace.setMap(map);
        return mapper.toDto(repository.save(workplace));
    }

    @Override
    public WorkplaceDto update(WorkplaceDto dto, long id) {
        Workplace workplace = repository.findById(id).orElse(null);
        if(workplace == null)
            return null;
        checkAndSet(workplace,dto);
        return mapper.toDto(repository.save(workplace));
    }

    @Override
    public void checkAndSet(Workplace workplace, WorkplaceDto workplaceDto) {
        if(workplace.getWorkplaceNumber() != workplaceDto.getWorkplaceNumber() && workplaceDto.getWorkplaceNumber() != 0)
            workplace.setWorkplaceNumber(workplaceDto.getWorkplaceNumber());
        if(workplaceDto.getHeadset() != null && workplace.getHeadset() != workplaceDto.getHeadset())
            workplace.setHeadset(workplaceDto.getHeadset());
        if(workplaceDto.getKeyboard() != null && workplace.getKeyboard() != workplaceDto.getKeyboard())
            workplace.setKeyboard(workplaceDto.getKeyboard());
        if(workplaceDto.getMonitor() != null && workplace.getMonitor() != workplaceDto.getMonitor())
            workplace.setMonitor(workplaceDto.getMonitor());
        if(workplaceDto.getMouse() != null && workplace.getMouse() != workplaceDto.getMouse())
            workplace.setMouse(workplaceDto.getMouse());
        if(workplaceDto.getNextToWindow() != null && workplace.getNextToWindow() != workplaceDto.getNextToWindow())
            workplace.setNextToWindow(workplaceDto.getNextToWindow());
    }
}
