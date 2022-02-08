package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.baseCrud.BaseCRUDService;
import com.exadel.sandbox.team2.domain.Map;
import org.springframework.data.repository.CrudRepository;

public class MapServiceImpl extends BaseCRUDService<Map, Long> {

    public MapServiceImpl(CrudRepository<Map, Long> repository) {
        super(repository);
    }

}
