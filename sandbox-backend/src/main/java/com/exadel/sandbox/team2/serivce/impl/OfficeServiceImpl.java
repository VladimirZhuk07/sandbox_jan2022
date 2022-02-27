package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.CityRepository;
import com.exadel.sandbox.team2.dao.OfficeRepository;
import com.exadel.sandbox.team2.domain.City;
import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.dto.OfficeDto;
import com.exadel.sandbox.team2.mapper.OfficeMapper;
import com.exadel.sandbox.team2.serivce.base.CRUDServiceImpl;
import com.exadel.sandbox.team2.serivce.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OfficeServiceImpl  extends CRUDServiceImpl<Office> implements OfficeService {

    //I had to call repository, since calling
    //through CrudServiceImpl won`t include new queries
    private final OfficeRepository repository;
    private final CityRepository cityRepository;

    private final OfficeMapper mapper;

    public Office getOffice(long id){
        return repository.findById(id).get();
    }

    public OfficeDto updateOffice(OfficeDto officeDto, long id) {
        Office office = repository.findById(id).get();
        if(!officeDto.getAddress().equals("string") && !officeDto.getAddress().equals(""))
            office.setAddress(officeDto.getAddress());
        if(!officeDto.getName().equals("string") && !officeDto.getName().equals(""))
            office.setName(officeDto.getName());

        return mapper.toDto(repository.save(office));
    }

    @Override
    public OfficeDto saveOffice(OfficeDto officeDto) {
        Optional<City> nCity = cityRepository.findById(officeDto.getCityName());
        if(nCity.isPresent()){
            Office office = mapper.toEntity(officeDto);
            office.setCity(nCity.get());
            return mapper.toDto(repository.save(office));
        }
        return null;
    }

    @Override
    public void deleteByCity(String city) {
        Optional<City> nCity = cityRepository.findById(city);
        nCity.ifPresent(repository::deleteByCity);
    }

    @Override
    public List<Office> findByCityName(String city) {
        Optional<City> nCity = cityRepository.findById(city);
        return new ArrayList<>(repository.findByCity(nCity.get()));
    }
}
