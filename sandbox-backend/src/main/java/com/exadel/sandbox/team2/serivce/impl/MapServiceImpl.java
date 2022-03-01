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
    public Map save(Map map, MapDto mapDto) {
        Office office = officeRepository.getById(mapDto.getOfficeId());
        if(office.getMap() == null) {
            map = mapper.toEntity(mapDto);
            map.setOfficeId(office);
            return repository.save(map);
        }

        return null;
    }

    @Override
    public Map update(Map map, MapDto mapDto, long id) {
        map = repository.getById(id);
        if(mapDto.getFloorNum() != 0 ){
            map.setFloorNum(mapDto.getFloorNum());
        }
        if(mapDto.getConfRoomsNum() != 0 ){
            map.setConfRoomsNum(mapDto.getConfRoomsNum());
        }
        if(mapDto.getKitchenNum() != 0 ){
            map.setKitchenNum(mapDto.getKitchenNum());
        }
        return repository.save(map);
    }

    @Override
    public void deleteByOfficeId(long officeId) {
        Office office = officeRepository.getById(officeId);
        repository.deleteByOfficeId(office);
    }
}
