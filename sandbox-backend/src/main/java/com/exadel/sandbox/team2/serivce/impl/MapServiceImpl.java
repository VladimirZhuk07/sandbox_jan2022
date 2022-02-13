package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.MapRepository;
import com.exadel.sandbox.team2.domain.Map;
import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.dto.MapDto;
import com.exadel.sandbox.team2.mapper.MapMapper;
import com.exadel.sandbox.team2.serivce.base.CRUDServiceImpl;
import com.exadel.sandbox.team2.serivce.service.MapService;
import com.exadel.sandbox.team2.serivce.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MapServiceImpl extends CRUDServiceImpl<Map> implements MapService {

    private final OfficeService officeService;

    private final MapMapper mapper;

    private final MapRepository repository;

    public MapDto saveMap(MapDto entity) {
        Office office = officeService.findById(entity.getOfficeId()).get();
        Map map = mapper.toEntity(entity);
        map.setOfficeId(office);
        map.setId(office.getId());
        repository.save(map);

        return mapper.toDto(map);
    }

    public MapDto updateMap(MapDto mapDto, long mapId) {
        Map map = repository.findById(mapId).get();
        if(mapDto.getFloorNum() != 0 ){
            map.setFloorNum(mapDto.getFloorNum());
        }
        if(mapDto.getConfRoomsNum() != 0 ){
            map.setConfRoomsNum(mapDto.getConfRoomsNum());
        }
        if(mapDto.getKitchenNum() != 0 ){
            map.setKitchenNum(mapDto.getKitchenNum());
        }
        return mapper.toDto(repository.save(map));
    }

    @Override
    public void deleteByOfficeId(long officeId) {

        Office office = officeService.findById(officeId).get();

        repository.deleteByOfficeId(office);
    }
}
