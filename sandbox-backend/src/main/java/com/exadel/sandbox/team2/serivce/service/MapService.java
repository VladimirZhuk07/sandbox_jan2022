package com.exadel.sandbox.team2.serivce.service;

import com.exadel.sandbox.team2.domain.Map;
import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.dto.MapDto;
import com.exadel.sandbox.team2.serivce.base.CrudService;

public interface MapService extends CrudService<Map,MapDto> {

    void deleteByOfficeId(Office office);


}
