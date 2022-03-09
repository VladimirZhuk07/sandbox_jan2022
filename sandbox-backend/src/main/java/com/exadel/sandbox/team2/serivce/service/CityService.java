package com.exadel.sandbox.team2.serivce.service;

import com.exadel.sandbox.team2.domain.City;
import com.exadel.sandbox.team2.dto.CityDto;
import com.exadel.sandbox.team2.serivce.base.BaseDtoService;

import java.util.List;

public interface CityService extends BaseDtoService<City, CityDto> {
    List<City> findByCountryName(String countryName);
}
