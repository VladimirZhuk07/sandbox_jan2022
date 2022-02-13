package com.exadel.sandbox.team2.mapper;

import com.exadel.sandbox.team2.domain.Map;
import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.dto.MapDto;
import com.exadel.sandbox.team2.serivce.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MapMapper {

    @Mapping(source = "officeId" ,target = "officeId", qualifiedByName = "getOfficeId")
    MapDto toDto(Map map);

    @Mapping(source = "officeId", target = "officeId", ignore = true)
    Map toEntity(MapDto mapDto);

    @Named("getOfficeId")
    static long getOfficeId(Office office){
        return office.getId();
    }

}
