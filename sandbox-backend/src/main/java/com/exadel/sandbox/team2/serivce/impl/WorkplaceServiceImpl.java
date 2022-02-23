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
public class WorkplaceServiceImpl extends CRUDServiceImpl<Workplace> implements WorkplaceService {

    private final WorkplaceRepository workplaceRepository;
    private final MapRepository mapRepository;
    private final WorkplaceMapper mapper;

    @Override
    public WorkplaceDto save(WorkplaceDto workplaceDto) {
        Workplace workplace = mapper.toEntity(workplaceDto);
        Map map = mapRepository.getOne(workplaceDto.getMapId());
        workplace.setMapId(map);
        Workplace newWorkplace = workplaceRepository.save(workplace);
        return mapper.toDto(newWorkplace);
    }

    @Override
    public WorkplaceDto update(Long id, WorkplaceDto workplaceDto) {
        Workplace workplace = workplaceRepository.findById(id).get();
        if(workplace.isHeadset() != workplaceDto.isHeadset())
            workplace.setHeadset(workplaceDto.isHeadset());
        if(workplace.isKeyboard() != workplaceDto.isKeyboard())
            workplace.setKeyboard(workplaceDto.isKeyboard());
        if(workplace.isMonitor() != workplaceDto.isMonitor())
            workplace.setMonitor(workplaceDto.isMonitor());
        if(workplace.isMouse() != workplaceDto.isMouse())
            workplace.setMouse(workplaceDto.isMouse());
        if(workplace.isNextToWindow() != workplaceDto.isNextToWindow())
            workplace.setNextToWindow(workplaceDto.isNextToWindow());
        if(workplace.isPc() != workplaceDto.isPc())
            workplace.setPc(workplaceDto.isPc());
        if(workplace.getWorkplaceNumber() == workplaceDto.getWorkplaceNumber())
            workplace.setWorkplaceNumber(workplaceDto.getWorkplaceNumber());

        return mapper.toDto(workplaceRepository.save(workplace));
    }
}
