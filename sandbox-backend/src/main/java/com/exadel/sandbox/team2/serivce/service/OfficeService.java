package com.exadel.sandbox.team2.serivce.service;

import com.exadel.sandbox.team2.domain.Country;
import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.dto.OfficeDto;
import com.exadel.sandbox.team2.serivce.base.CRUDService;

import java.util.List;

public interface OfficeService extends CRUDService<Office> {

    Office getOffice(long id);

    OfficeDto updateOffice(OfficeDto officeDto, long id);

    void deleteByCountry(String country);

    List<Office> findByCountryName(Country country);

}
