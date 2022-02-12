package com.exadel.sandbox.team2.serivce.service;

import com.exadel.sandbox.team2.domain.Country;
import com.exadel.sandbox.team2.dto.CountryDto;

import java.util.List;

public interface CountryService {

    Country findByName(String name);

    List<Country> findAll();

    CountryDto save(CountryDto country);

    void deleteByName(String name);


}
