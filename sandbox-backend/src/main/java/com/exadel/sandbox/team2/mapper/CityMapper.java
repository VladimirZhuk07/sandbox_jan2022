package com.exadel.sandbox.team2.mapper;

import com.exadel.sandbox.team2.domain.City;
import com.exadel.sandbox.team2.domain.Country;
import com.exadel.sandbox.team2.dto.CityDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CityMapper {

    @Mapping(source = "country" ,target = "countryId", qualifiedByName = "getCountryId")
    CityDto toDto(City city);
    City toEntity(CityDto cityDto);

    @Named("getCountryId")
    static long getCountryId(Country country){
        return country.getId();
    }

}