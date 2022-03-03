package com.exadel.sandbox.team2.serivce.service;

import com.exadel.sandbox.team2.domain.Map;
import com.exadel.sandbox.team2.dto.MapDto;
import com.exadel.sandbox.team2.serivce.base.BaseDtoService;

public interface MapService extends BaseDtoService<Map,MapDto> {

    void deleteByOfficeId(long officeId);
}
