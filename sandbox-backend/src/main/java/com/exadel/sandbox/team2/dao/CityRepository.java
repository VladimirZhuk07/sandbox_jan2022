package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByCountryName(String countryName);
}
