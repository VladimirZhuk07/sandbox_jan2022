package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.Workplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkplaceRepository extends JpaRepository<Workplace, Long> {
}
