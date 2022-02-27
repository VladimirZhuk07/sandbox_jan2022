package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.CityRepository;
import com.exadel.sandbox.team2.domain.City;
import com.exadel.sandbox.team2.dto.CityDto;
import com.exadel.sandbox.team2.mapper.CityMapper;
import com.exadel.sandbox.team2.serivce.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository repository;
    private final CityMapper mapper;

    @Override
    public City findByName(String name) {
        return repository.findById(name).get();
    }

    @Override
    public List<City> findAll() {
        List<City> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public CityDto save(CityDto cityDto) {
        return mapper.toDto(repository.save(mapper.toEntity(cityDto)));
    }

    @Override
    public void deleteByName(String name) {
        repository.deleteById(name);
    }
}
