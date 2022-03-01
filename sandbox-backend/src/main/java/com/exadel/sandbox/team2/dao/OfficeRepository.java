package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.Country;
import com.exadel.sandbox.team2.domain.Office;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeRepository  extends CrudRepository<Office, Long>{

    void deleteByCountryName(Country country);

    List<Office> findByCountryName(Country country);

}
