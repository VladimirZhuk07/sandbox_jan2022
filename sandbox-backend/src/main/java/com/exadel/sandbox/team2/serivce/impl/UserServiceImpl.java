package com.exadel.sandbox.team2.serivce.impl;

import com.exadel.sandbox.team2.dao.UserRepository;
import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.serivce.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repository;

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }
    public Optional<User> findByUsername(String username){
        return repository.findByUsername(username);
    }
    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }


    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public User update(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser=repository.findByUsername(username);
        if(optionalUser.isPresent()){
            return new UserDetails() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return new ArrayList<>(Arrays.asList(()->"ADMIN"));
                }

                @Override
                public String getPassword() {
                    return "admin";
                }

                @Override
                public String getUsername() {
                    return optionalUser.get().getUsername();
                }

                @Override
                public boolean isAccountNonExpired() {
                    return true;
                }

                @Override
                public boolean isAccountNonLocked() {
                    return true;
                }

                @Override
                public boolean isCredentialsNonExpired() {
                    return true;
                }

                @Override
                public boolean isEnabled() {
                    return true;
                }
            };
        }
        throw new UsernameNotFoundException("Incorrect username or password!");
    }
}
