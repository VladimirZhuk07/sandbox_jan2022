package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.RoleRepository;
import com.exadel.sandbox.team2.dao.UserRepository;
import com.exadel.sandbox.team2.domain.Role;
import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.exception.RoleNotFoundException;
import com.exadel.sandbox.team2.exception.UserNotFoundException;
import com.exadel.sandbox.team2.serivce.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
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
        User user  = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        Role role = roleRepository.findById(roleId).orElse(null);
        if (role == null) {
            throw new RoleNotFoundException();
        }
        Set<Role> userRoles = user.getRoles();
        userRoles.add(role);
        user.setRoles(userRoles);
        userRepository.save(user);
    }

    @Override
    public void unassignUserRole(Long userId, Long roleId) {
        User user  = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }
        user.getRoles().removeIf(x -> x.getId().equals(roleId));
        userRepository.save(user);
    }
}
