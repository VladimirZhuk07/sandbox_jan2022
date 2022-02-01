package com.exadel.sandbox.team2.dao;

import com.exadel.sandbox.team2.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
