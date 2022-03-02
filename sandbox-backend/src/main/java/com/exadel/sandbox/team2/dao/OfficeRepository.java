package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.City;
import com.exadel.sandbox.team2.domain.Country;
import com.exadel.sandbox.team2.domain.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeRepository  extends JpaRepository<Office, Long> {

    void deleteByCityId(City city);

    List<Office> findByCityId(City city);

}
