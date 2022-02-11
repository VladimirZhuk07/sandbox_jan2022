package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.Map;
import com.exadel.sandbox.team2.domain.Office;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRepository  extends CrudRepository<Map, Long> {

    void deleteByOfficeId(Office office);

}
