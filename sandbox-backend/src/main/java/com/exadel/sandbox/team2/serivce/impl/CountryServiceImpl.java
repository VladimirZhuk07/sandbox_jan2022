package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.CountryRepository;
import com.exadel.sandbox.team2.domain.Country;
import com.exadel.sandbox.team2.dto.CountryDto;
import com.exadel.sandbox.team2.mapper.CountryMapper;
import com.exadel.sandbox.team2.serivce.base.CRUDServiceImpl;
import com.exadel.sandbox.team2.serivce.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl extends CRUDServiceImpl<Country, CountryDto> implements CountryService {

    private final CountryRepository repository;
    private final CountryMapper mapper;

    @Override
    public CountryDto updateDto(CountryDto dto, long id) {
        Optional<Country> isExist = repository.findById(id);
        if(isExist.isPresent()){
            Country country = isExist.get();
            checkAndSet(country,dto);
            return mapper.toDto(repository.save(country));
        }
        return null;
    }

    @Override
    public void checkAndSet(Country country, CountryDto countryDto) {
        if(countryDto.getName() != null && !country.getName().equals(countryDto.getName()) && !countryDto.getName().equals("string"))
            country.setName(countryDto.getName());
    }

    @Override
    public CountryDto saveDto(CountryDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }
}
