package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.RoleRepository;
import com.exadel.sandbox.team2.dao.UserRepository;
import com.exadel.sandbox.team2.domain.Role;
import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.UserState;
import com.exadel.sandbox.team2.mapper.UserMapper;
import com.exadel.sandbox.team2.serivce.base.CRUDServiceImpl;
import com.exadel.sandbox.team2.serivce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CRUDServiceImpl<User> implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper mapper;

    @Override
    public Set<Role> getRoles(User user) {
        return user.getRoles();
    }

    @Override
    public void assignUserRole(Long userId, Long roleId) {
        User user = userRepository.findById(userId).orElse(null);
        Role role = roleRepository.findById(roleId).orElse(null);
        Set<Role> userRoles = user.getRoles();
        userRoles.add(role);
        user.setRoles(userRoles);
        userRepository.save(user);
    }

    @Override
    public void unassignUserRole(Long userId, Long roleId) {
        User user  = userRepository.findById(userId).orElse(null);
        user.getRoles().removeIf(x -> x.getId().equals(roleId));
        userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByAuthorizationCode(String code) {
        return userRepository.findByTelegramAuthorizationCode(code);
    }

    @Override
    public Optional<User> getUserByTelegramChatIdOrPhone(String chatId, String phone) {
        return userRepository.findByChatIdOrPhoneNumber(chatId, phone);
    }

    @Override
    public List<User> findAllByStatus(UserState state) {
        return userRepository.findAllByStatus(state);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByEmail(username);
    }

}
