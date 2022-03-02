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


@Service
@RequiredArgsConstructor
public class OfficeServiceImpl  extends CRUDServiceImpl<Office, OfficeDto> implements OfficeService {

    private final OfficeRepository repository;
    private final CityRepository cityRepository;
    private final OfficeMapper mapper;

    @Override
    public Office save(Office office, OfficeDto officeDto) {
        City city = cityRepository.getById(officeDto.getCityId());
        office = mapper.toEntity(officeDto);
        office.setCityId(city);
        return repository.save(office);
    }

    @Override
    public Office update(Office office, OfficeDto officeDto, long id) {
        office = repository.findById(id).get();
        if(!officeDto.getAddress().equals("string") && !officeDto.getAddress().equals(""))
            office.setAddress(officeDto.getAddress());
        if(!officeDto.getName().equals("string") && !officeDto.getName().equals(""))
            office.setName(officeDto.getName());

        return repository.save(office);
    }

    @Override
    public void deleteByCity(long id) {
        City city = cityRepository.getById(id);
        repository.deleteByCityId(city);
    }

    @Override
    public List<Office> findByCityId(long id) {
        City city = cityRepository.findById(id).get();
        return new ArrayList<>(repository.findByCityId(city)).stream().toList();
    }
}
