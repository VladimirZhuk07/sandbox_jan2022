package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
