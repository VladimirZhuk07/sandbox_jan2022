package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.Workplace;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkplaceRepository extends CrudRepository<Workplace, Long> {
}
