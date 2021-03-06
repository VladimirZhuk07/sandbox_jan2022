package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.MapRepository;
import com.exadel.sandbox.team2.dao.OfficeRepository;
import com.exadel.sandbox.team2.domain.Map;
import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.dto.MapDto;
import com.exadel.sandbox.team2.dto.report.ReportOnFloorDto;
import com.exadel.sandbox.team2.mapper.MapMapper;
import com.exadel.sandbox.team2.serivce.base.CRUDServiceImpl;
import com.exadel.sandbox.team2.serivce.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MapServiceImpl extends CRUDServiceImpl<Map> implements MapService {

    private final OfficeRepository officeRepository;

    private final MapMapper mapper;

    private final MapRepository repository;

    @Override
    public void delete(Long id) {
        Office office = repository.getById(id).getOffice();
        office.setMap(null);
        officeRepository.save(office);
        repository.deleteById(id);
    }

    @Override
    public MapDto save(MapDto dto) {
        Office office = officeRepository.getById(dto.getOfficeId());
        if(office.getMap() == null) {
            Map map = mapper.toEntity(dto);
            map.setOffice(office);
            return mapper.toDto(repository.save(map));
        }

        return null;
    }

    @Override
    public MapDto update(MapDto dto, long id) {
        Map map = repository.findById(id).orElse(null);
        if(map == null)
            return null;
        checkAndSet(map,dto);
        return mapper.toDto(repository.save(map));
    }

    @Override
    public void checkAndSet(Map map, MapDto mapDto) {
        if(map.getFloorNum() != mapDto.getFloorNum() && mapDto.getFloorNum() != 0)
            map.setFloorNum(mapDto.getFloorNum());
        if(map.getKitchenNum() != mapDto.getKitchenNum() && mapDto.getKitchenNum() != 0)
            map.setKitchenNum(mapDto.getKitchenNum());
        if(map.getConfRoomsNum() != mapDto.getConfRoomsNum() && mapDto.getConfRoomsNum() != 0)
            map.setConfRoomsNum(mapDto.getConfRoomsNum());
    }

    @Override
    public void deleteByOfficeId(long officeId) {
        repository.deleteByOfficeId(officeId);
    }

    @Override
    public Map findByOfficeId(Long id) {
        return repository.findByOfficeId(id).orElse(null);
    }

    @Override
    public List<ReportOnFloorDto> getDataForReportOnFloor(Long idOfFloor, Date bookedDateFrom, Date bookedDateTo) {
        return repository.getDataForReportOnFloor(idOfFloor, bookedDateFrom, bookedDateTo);
    }
}
