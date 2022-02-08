package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.baseCrud.BaseCRUDService;
import com.exadel.sandbox.team2.domain.Office;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class OfficeServiceImpl extends BaseCRUDService<Office, Long> {

    public OfficeServiceImpl(CrudRepository<Office, Long> repository) {
        super(repository);
    }

}
