package com.exadel.sandbox.team2.mapper;

import com.exadel.sandbox.team2.domain.City;
import com.exadel.sandbox.team2.dto.CityDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityDto toDto(City city);

    City toEntity(CityDto cityDto);

}
