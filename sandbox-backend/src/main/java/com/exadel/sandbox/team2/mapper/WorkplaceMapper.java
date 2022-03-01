package com.exadel.sandbox.team2.mapper;

import com.exadel.sandbox.team2.domain.Map;
import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.domain.Workplace;
import com.exadel.sandbox.team2.dto.WorkplaceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface WorkplaceMapper {
    @Mapping(source = "mapId" ,target = "mapId", qualifiedByName = "getMapId")
    WorkplaceDto toDto(Workplace workplace);
    @Mapping(source = "mapId", target = "mapId", ignore = true)
    Workplace toEntity(WorkplaceDto workplaceDto);

    @Named("getMapId")
    static long getMapId(Map map){
        return map.getId();
    }
}
