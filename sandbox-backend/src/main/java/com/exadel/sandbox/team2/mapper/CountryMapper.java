package com.exadel.sandbox.team2.mapper;

import com.exadel.sandbox.team2.domain.Country;
import com.exadel.sandbox.team2.dto.CountryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    CountryDto toDto(Country country);

    Country toEntity(CountryDto countryDto);

}
