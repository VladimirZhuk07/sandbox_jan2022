package com.exadel.sandbox.team2.serivce.service;

import com.exadel.sandbox.team2.domain.Role;
import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.UserState;
import com.exadel.sandbox.team2.serivce.base.CRUDService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService extends CRUDService<User> {

    Set<Role> getRoles(User user);

    void assignUserRole(Long userId, Long roleId);

    void unassignUserRole(Long userId, Long roleId);

    Optional<User> findInvitedUserByAuthorizationCode(String code);

    Optional<User> findUserByChatIdOrPhoneNumber(String chatId,String phone);

    List<User> findAllByStatus(UserState state);

    Optional<User> findByUsername(String  username);

    Optional<User> findActiveUserByChatId(String chatId);
}