package com.exadel.sandbox.team2.serivce.service;

import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.dto.OfficeDto;
import com.exadel.sandbox.team2.serivce.base.CRUDService;

import java.util.List;

public interface OfficeService extends CRUDService<Office> {

    Office getOffice(long id);

    OfficeDto updateOffice(OfficeDto officeDto, long id);

    OfficeDto saveOffice(OfficeDto officeDto);

    void deleteByCity(String city);

    List<Office> findByCityName(String city);

}
