package com.exadel.sandbox.team2.serivce.service;

import com.exadel.sandbox.team2.domain.Map;
import com.exadel.sandbox.team2.dto.MapDto;
import com.exadel.sandbox.team2.serivce.base.CRUDService;

import java.util.Optional;

public interface MapService extends CRUDService<Map> {

    MapDto saveMap(MapDto mapDto);

    MapDto updateMap(MapDto mapDto, long mapId);

    void deleteByOfficeId(long officeId);


}
