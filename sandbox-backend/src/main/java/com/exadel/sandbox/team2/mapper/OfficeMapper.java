package com.exadel.sandbox.team2.mapper;

import com.exadel.sandbox.team2.domain.City;
import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.dto.OfficeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OfficeMapper {

    @Mapping(source = "cityId" ,target = "cityId", qualifiedByName = "getCityId")
    OfficeDto toDto(Office office);

    @Mapping(source = "cityId", target = "cityId", ignore = true)
    Office toEntity(OfficeDto officeDto);

    @Named("getCityId")
    static long getCityId(City city){
        return city.getId();
    }

}
