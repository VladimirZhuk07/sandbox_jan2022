package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByCountryName(String countryName);

    Optional<City> findByName(String name);
}
