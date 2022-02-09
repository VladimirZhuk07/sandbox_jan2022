package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.baseCrud.BaseCRUDService;
import com.exadel.sandbox.team2.domain.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CountryServiceImpl extends BaseCRUDService<Country, Long> {

    public CountryServiceImpl(CrudRepository<Country, Long> repository) {
        super(repository);
    }

}
