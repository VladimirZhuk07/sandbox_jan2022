package com.exadel.sandbox.team2.mapper;

import com.exadel.sandbox.team2.domain.City;
import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.dto.OfficeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface OfficeMapper {

    @Mapping(source = "city" ,target = "cityName", qualifiedByName = "getCityName")
    OfficeDto toDto(Office office);

    Office toEntity(OfficeDto officeDto);

    @Named("getCityName")
    static String getCityName(City city){
        return city.getName();
    }

}
