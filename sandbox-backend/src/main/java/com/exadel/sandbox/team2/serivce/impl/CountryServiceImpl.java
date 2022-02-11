package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.CountryRepository;
import com.exadel.sandbox.team2.domain.Country;
import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.serivce.service.CountryService;
import com.exadel.sandbox.team2.serivce.service.MapService;
import com.exadel.sandbox.team2.serivce.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {


    private final CountryRepository repository;
    private final OfficeService officeService;
    private final MapService mapService;

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
    public Country save(Country country) {
        return repository.save(country);
    }

    @Override
    public void deleteByName(String name) {

        Country country = repository.findByName(name);

        List<Office> offices = officeService.findByOfficeName(country);

        for(Office office: offices){
            mapService.deleteByOfficeId(office);
        }

        officeService.deleteByCountry(name);

        repository.deleteByName(name);
    }


}
