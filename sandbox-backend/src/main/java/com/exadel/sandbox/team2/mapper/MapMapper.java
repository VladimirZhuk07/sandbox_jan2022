package com.exadel.sandbox.team2.mapper;

import com.exadel.sandbox.team2.domain.Map;
import com.exadel.sandbox.team2.dto.MapDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapMapper {

    MapDto toDto(Map map);

    Map toEntity(MapDto mapDto);

}
