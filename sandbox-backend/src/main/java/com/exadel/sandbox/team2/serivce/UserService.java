package com.exadel.sandbox.team2.serivce;

import com.exadel.sandbox.team2.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(Long id);

    List<User> findAll();

    User save(User user);

    User update(User user);

    void delete(Long id);
}
