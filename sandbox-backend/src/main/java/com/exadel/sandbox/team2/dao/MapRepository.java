package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.Map;
import com.exadel.sandbox.team2.domain.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRepository  extends JpaRepository<Map, Long> {
    void deleteByOfficeId(Office office);
}
