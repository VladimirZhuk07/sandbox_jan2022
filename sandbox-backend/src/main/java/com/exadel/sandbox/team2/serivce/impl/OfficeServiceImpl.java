package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.CountryRepository;
import com.exadel.sandbox.team2.dao.OfficeRepository;
import com.exadel.sandbox.team2.domain.Country;
import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.dto.OfficeDto;
import com.exadel.sandbox.team2.mapper.CountryMapper;
import com.exadel.sandbox.team2.mapper.OfficeMapper;
import com.exadel.sandbox.team2.serivce.base.CRUDServiceImpl;
import com.exadel.sandbox.team2.serivce.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OfficeServiceImpl  extends CRUDServiceImpl<Office, OfficeDto> implements OfficeService {

    //I had to call repository, since calling
    //through CrudServiceImpl won`t include new queries
    private final OfficeRepository repository;
    private final CountryRepository countryRepository;

    private final OfficeMapper mapper;
    private final CountryMapper countryMapper;

    @Override
    public Office update(Office office, OfficeDto officeDto, long id) {
        office = repository.findById(id).get();
        if(!officeDto.getCity().equals("string") && !officeDto.getCity().equals(""))
            office.setCity(officeDto.getCity());
        if(!officeDto.getAddress().equals("string") && !officeDto.getAddress().equals(""))
            office.setAddress(officeDto.getAddress());
        if(!officeDto.getName().equals("string") && !officeDto.getName().equals(""))
            office.setName(officeDto.getName());

        return repository.save(office);
    }

    @Override
    public void deleteByCountry(String country) {
        Country country1 = countryRepository.findByName(country);
        repository.deleteByCountryName(country1);
    }

    @Override
    public List<Office> findByCountryName(Country country) {
        return repository.findByCountryName(country);
    }
}
