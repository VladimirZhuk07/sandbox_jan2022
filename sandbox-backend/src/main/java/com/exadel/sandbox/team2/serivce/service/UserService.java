package com.exadel.sandbox.team2.serivce.service;

import com.exadel.sandbox.team2.domain.Role;
import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.serivce.base.CRUDService;

import java.util.Set;

public interface UserService extends CRUDService<User> {

    Set<Role> getRoles(User user);

    void assignUserRole(Long userId, Long roleId);

    void unassignUserRole(Long userId, Long roleId);
}