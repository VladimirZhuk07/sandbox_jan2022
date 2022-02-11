package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.CountryRepository;
import com.exadel.sandbox.team2.dao.OfficeRepository;
import com.exadel.sandbox.team2.domain.Country;
import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.serivce.base.CrudServiceImp;
import com.exadel.sandbox.team2.serivce.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OfficeServiceImpl  extends CrudServiceImp<Office> implements OfficeService {

    //I had to call repository, since calling
    //through CrudServiceImpl won`t include new queries
    private final OfficeRepository repository;
    private final CountryRepository countryRepository;

    public Optional<Office> findById(Long id) {
        return repository.findById(id);
    }

    public List<Office> findAll() {
        List<Office> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }


    public Office save(Office office) {
        return repository.save(office);
    }

    public Office update(Office office) {
        return repository.save(office);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByCountry(String country) {

        Country country1 = countryRepository.findByName(country);

        repository.deleteByCountryName(country1);
    }

    @Override
    public List<Office> findByOfficeName(Country country) {
        return repository.findByCountryName(country);
    }
}
