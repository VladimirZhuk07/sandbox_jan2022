package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.WorkplaceRepository;
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

    private final WorkplaceMapper mapper;

    @Override
    public WorkplaceDto save(WorkplaceDto workplaceDto) {
        Workplace workplace = mapper.toEntity(workplaceDto);
        Workplace newWorkplace = workplaceRepository.save(workplace);
        return mapper.toDto(newWorkplace);
    }

    @Override
    public WorkplaceDto update(Long id, WorkplaceDto workplaceDto) {
        Workplace workplace = workplaceRepository.findById(id).get();
        if(workplace.isHeadset() != workplaceDto.getHeadset())
            workplace.setHeadset(workplaceDto.getHeadset());
        if(workplace.isKeyboard() != workplaceDto.getKeyboard())
            workplace.setKeyboard(workplaceDto.getKeyboard());
        if(workplace.isMonitor() != workplaceDto.getMonitor())
            workplace.setMonitor(workplaceDto.getMonitor());
        if(workplace.isMouse() != workplaceDto.getMouse())
            workplace.setMouse(workplaceDto.getMouse());
        if(workplace.isNextToWindow() != workplaceDto.getNextToWindow())
            workplace.setNextToWindow(workplaceDto.getNextToWindow());
        if(workplace.isPc() != workplaceDto.getPc())
            workplace.setPc(workplaceDto.getPc());
        if(workplace.getWorkplaceNumber().equals(workplaceDto.getWorkplaceNumber()))
            workplace.setWorkplaceNumber(workplaceDto.getWorkplaceNumber());

        return mapper.toDto(workplaceRepository.save(workplace));
    }
}
