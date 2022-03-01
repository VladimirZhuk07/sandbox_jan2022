package com.exadel.sandbox.team2.service;

import com.exadel.sandbox.team2.domain.Role;
import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.RoleType;
import com.exadel.sandbox.team2.dto.UserDetailsDto;
import com.exadel.sandbox.team2.serivce.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser = userService.findByUsername(username);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setRoles(new HashSet<>(List.of(new Role(RoleType.USER))));
            return new UserDetailsDto(user);
        }
        throw new UsernameNotFoundException("Incorrect username or password");
    }
}
