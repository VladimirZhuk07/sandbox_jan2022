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

@Service
@RequiredArgsConstructor
public class MapServiceImpl extends CRUDServiceImpl<Map> implements MapService {

    private final OfficeService officeService;

    private final MapMapper mapper;

    private final MapRepository repository;

    public void deleteMap(Long id){
        Office office = repository.findById(id).get().getOfficeId();
        office.setMap(null);
        officeService.save(office);
        repository.deleteById(id);
    }

    public MapDto saveMap(MapDto entity) {
        Office office = officeService.findById(entity.getOfficeId()).get();
        if(office.getMap() == null) {
            Map map = mapper.toEntity(entity);
            map.setOfficeId(office);
            Map newMap = repository.save(map);
            return mapper.toDto(newMap);
        }

        return null;
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
