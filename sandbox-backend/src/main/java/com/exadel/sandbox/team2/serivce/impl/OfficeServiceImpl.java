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
    public OfficeDto save(OfficeDto dto) {
        City city = cityRepository.getById(dto.getCityId());
        Office office = mapper.toEntity(dto);
        office.setCity(city);
        return mapper.toDto(repository.save(office));
    }

    @Override
    public OfficeDto update(OfficeDto dto, long id) {
        Office office = repository.findById(id).orElse(null);
        if(office == null)
            return null;
        checkAndSet(office,dto);
        return mapper.toDto(repository.save(office));
    }

    @Override
    public void checkAndSet(Office office, OfficeDto officeDto) {
        if(officeDto.getName() != null && !office.getName().equals(officeDto.getName()) && !officeDto.getName().equals("string"))
            office.setName(officeDto.getName());
        if(officeDto.getAddress() != null && !office.getAddress().equals(officeDto.getAddress()) && !officeDto.getAddress().equals("string"))
            office.setAddress(officeDto.getAddress());
        if(officeDto.getParking() != null && office.getParking() != officeDto.getParking())
            office.setParking(officeDto.getParking());
    }

    @Override
    public void deleteByCity(long id) {
        repository.deleteByCityId(id);
    }

    @Override
    public List<Office> findByCityId(long id) {
        return new ArrayList<>(repository.findByCityId(id));
    }
}
