package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.MapRepository;
import com.exadel.sandbox.team2.dao.OfficeRepository;
import com.exadel.sandbox.team2.domain.Map;
import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.dto.MapDto;
import com.exadel.sandbox.team2.mapper.MapMapper;
import com.exadel.sandbox.team2.serivce.base.CRUDServiceImpl;
import com.exadel.sandbox.team2.serivce.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MapServiceImpl extends CRUDServiceImpl<Map,MapDto> implements MapService {

    private final OfficeRepository officeRepository;

    private final MapMapper mapper;

    private final MapRepository repository;

    @Override
    public void delete(Long id) {
        Office office = repository.getById(id).getOfficeId();
        office.setMap(null);
        officeRepository.save(office);
        repository.deleteById(id);
    }

    @Override
    public MapDto saveDto(MapDto dto) {
        Office office = officeRepository.getById(dto.getOfficeId());
        if(office.getMap() == null) {
            Map map = mapper.toEntity(dto);
            map.setOfficeId(office);
            return mapper.toDto(repository.save(map));
        }

        return null;
    }

    @Override
    public MapDto updateDto(MapDto dto, long id) {
        Optional<Map> isExist = repository.findById(id);
        if(isExist.isPresent()){
            Map map = isExist.get();
            checkAndSet(map,dto);
            return mapper.toDto(repository.save(map));
        }
        return null;
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
        Office office = officeRepository.getById(officeId);
        repository.deleteByOfficeId(office);
    }
}
