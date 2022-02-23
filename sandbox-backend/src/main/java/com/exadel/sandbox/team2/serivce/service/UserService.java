package com.exadel.sandbox.team2.serivce.service;

import com.exadel.sandbox.team2.domain.Role;
import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.serivce.base.CRUDService;

import java.util.Optional;
import java.util.Set;

public interface UserService extends CRUDService<User> {

    Set<Role> getRoles(User user);

    void assignUserRole(Long userId, Long roleId);

    void unassignUserRole(Long userId, Long roleId);

    Optional<User> getUserByAuthorizationCode(String code);

    Optional<User> getUserByTelegramChatIdOrPhone(String chatId,String phone);

}