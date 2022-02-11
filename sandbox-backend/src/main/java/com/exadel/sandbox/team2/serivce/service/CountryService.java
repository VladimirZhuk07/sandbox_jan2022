package com.exadel.sandbox.team2.serivce.service;

import com.exadel.sandbox.team2.domain.Country;

import java.util.List;
import java.util.Optional;

public interface CountryService {

    Country findByName(String name);

    List<Country> findAll();

    Country save(Country country);

    void deleteByName(String name);


}
