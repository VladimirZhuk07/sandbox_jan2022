package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.Country;
import com.exadel.sandbox.team2.domain.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeRepository  extends JpaRepository<Office, Long> {

    void deleteByCountryName(Country country);

    @Query("select o from Office o where o.countryName = ?1")
    List<Office> findByCountryName(Country country);

}
