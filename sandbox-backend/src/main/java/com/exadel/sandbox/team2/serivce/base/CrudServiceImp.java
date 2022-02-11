package com.exadel.sandbox.team2.serivce.base;

import com.exadel.sandbox.team2.domain.Country;
import com.exadel.sandbox.team2.domain.Office;
import com.exadel.sandbox.team2.domain.base.BaseEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class CrudServiceImp<E extends BaseEntity>{

    protected CrudRepository<E, Long> repository;

    public void setRepository(CrudRepository<E, Long> repository) {
        this.repository = repository;
    }


}
