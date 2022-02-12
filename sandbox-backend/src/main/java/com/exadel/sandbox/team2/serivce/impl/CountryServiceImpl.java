package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.CountryRepository;
import com.exadel.sandbox.team2.domain.Country;
import com.exadel.sandbox.team2.dto.CountryDto;
import com.exadel.sandbox.team2.mapper.CountryMapper;
import com.exadel.sandbox.team2.serivce.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {


    private final CountryRepository repository;
    private final CountryMapper mapper;

    @Override
    public Country findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Country> findAll() {
        List<Country> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public CountryDto save(CountryDto country) {
        return mapper.toDto(repository.save(mapper.toEntity(country)));
    }

    @Override
    public void deleteByName(String name) {
        repository.deleteByName(name);
    }


}
