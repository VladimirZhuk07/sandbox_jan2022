package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    Country findByName(String name);

    void deleteByName(String name);

}
