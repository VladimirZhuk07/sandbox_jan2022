package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.RoleRepository;
import com.exadel.sandbox.team2.dao.UserRepository;
import com.exadel.sandbox.team2.domain.Role;
import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.exception.NotFoundException;
import com.exadel.sandbox.team2.serivce.base.CRUDServiceImpl;
import com.exadel.sandbox.team2.serivce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CRUDServiceImpl<User> implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public Set<Role> getRoles(User user) {
        return user.getRoles();
    }

    @Override
    public void assignUserRole(Long userId, Long roleId) {
        User user = userRepository.findById(userId).orElse(null);
        if (null == user) {
            throw new NotFoundException("User with " + userId + " not found");
        }
        Role role = roleRepository.findById(roleId).orElse(null);
        if (null == role) {
            throw new NotFoundException("Role not found");
        }
        Set<Role> userRoles = user.getRoles();
        userRoles.add(role);
        user.setRoles(userRoles);
        userRepository.save(user);
    }

    @Override
    public void unassignUserRole(Long userId, Long roleId) {
        User user = userRepository.findById(userId).orElse(null);
        if (null == user) {
            throw new NotFoundException("User with " + userId + " not found");
        }
        user.getRoles().removeIf(x -> x.getId().equals(roleId));
        userRepository.save(user);
    }
}
