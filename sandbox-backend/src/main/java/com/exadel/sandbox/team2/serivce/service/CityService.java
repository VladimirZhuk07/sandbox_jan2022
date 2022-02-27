package com.exadel.sandbox.team2.serivce.service;

import com.exadel.sandbox.team2.domain.City;
import com.exadel.sandbox.team2.dto.CityDto;

import java.util.List;

public interface CityService {

    City findByName(String name);

    List<City> findAll();

    CityDto save(CityDto cityDto);

    void deleteByName(String name);

}
