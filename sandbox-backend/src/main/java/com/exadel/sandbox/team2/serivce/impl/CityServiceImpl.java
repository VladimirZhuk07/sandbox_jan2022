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
public class CityServiceImpl extends CRUDServiceImpl<City> implements CityService {

    private final CityRepository repository;
    private final CountryRepository countryRepository;
    private final CityMapper mapper;

    @Override
    public CityDto save(CityDto dto) {
        Country country = countryRepository.getById(dto.getCountryId());
        City city = mapper.toEntity(dto);
        city.setCountry(country);
        return mapper.toDto(repository.save(city));
    }

    @Override
    public CityDto update(CityDto dto, long id) {
        City city = repository.findById(id).orElse(null);
        if(city == null)
            return null;
        checkAndSet(city,dto);
        return mapper.toDto(repository.save(city));
    }

    @Override
    public void checkAndSet(City city, CityDto cityDto) {
        if(cityDto.getName() != null && !city.getName().equals(cityDto.getName()) && !cityDto.getName().equals("string"))
            city.setName(cityDto.getName());
    }
}