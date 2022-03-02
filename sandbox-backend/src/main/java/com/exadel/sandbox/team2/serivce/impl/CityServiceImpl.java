package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.CityRepository;
import com.exadel.sandbox.team2.dao.CountryRepository;
import com.exadel.sandbox.team2.domain.City;
import com.exadel.sandbox.team2.domain.Country;
import com.exadel.sandbox.team2.dto.CityDto;
import com.exadel.sandbox.team2.mapper.CityMapper;
import com.exadel.sandbox.team2.serivce.base.CRUDServiceImpl;
import com.exadel.sandbox.team2.serivce.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityServiceImpl extends CRUDServiceImpl<City, CityDto> implements CityService {

    private final CityRepository repository;
    private final CountryRepository countryRepository;
    private final CityMapper mapper;

    @Override
    public City save(City city, CityDto cityDto) {
        Country country = countryRepository.getById(cityDto.getCountryId());
        city = mapper.toEntity(cityDto);
        city.setCountryId(country);
        return repository.save(city);
    }

    @Override
    public City update(City city, CityDto cityDto, long id) {
        city = repository.findById(id).get();
        if(!cityDto.getName().equals(city.getName()))
            city.setName(cityDto.getName());

        return repository.save(city);
    }
}
