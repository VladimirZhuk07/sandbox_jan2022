package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.CountryRepository;
import com.exadel.sandbox.team2.domain.Country;
import com.exadel.sandbox.team2.dto.CountryDto;
import com.exadel.sandbox.team2.serivce.base.CRUDServiceImpl;
import com.exadel.sandbox.team2.serivce.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl extends CRUDServiceImpl<Country, CountryDto> implements CountryService {

    private final CountryRepository repository;

    @Override
    public Country update(Country country, CountryDto countryDto, long id) {
        country = repository.findById(id).get();
        country.setName(countryDto.getName());
        return repository.save(country);
    }
}
