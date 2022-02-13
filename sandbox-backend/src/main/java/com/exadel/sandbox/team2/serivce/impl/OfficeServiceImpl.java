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
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OfficeServiceImpl  extends CRUDServiceImpl<Office> implements OfficeService {

    //I had to call repository, since calling
    //through CrudServiceImpl won`t include new queries
    private final OfficeRepository repository;
    private final CountryRepository countryRepository;

    private final OfficeMapper mapper;
    private final CountryMapper countryMapper;

    public Office getOffice(long id){
        return repository.findById(id).get();
    }

    public OfficeDto updateOffice(OfficeDto officeDto, long id) {
        Office office = repository.findById(id).get();
        if(!officeDto.getCity().equals("string") && !officeDto.getCity().equals(""))
            office.setCity(officeDto.getCity());
        if(!officeDto.getAddress().equals("string") && !officeDto.getAddress().equals(""))
            office.setAddress(officeDto.getAddress());
        if(!officeDto.getCountryName().getName().equals("string") && !officeDto.getCountryName().getName().equals(""))
            office.setCountryName(countryMapper.toEntity(officeDto.getCountryName()));
        if(!officeDto.getName().equals("string") && !officeDto.getName().equals(""))
            office.setName(officeDto.getName());

        return mapper.toDto(repository.save(office));
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
