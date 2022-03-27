package com.exadel.sandbox.team2.service;

import com.exadel.sandbox.team2.component.JwtTokenProvider;
import com.exadel.sandbox.team2.domain.Role;
import com.exadel.sandbox.team2.domain.User;
import com.exadel.sandbox.team2.domain.enums.RoleType;
import com.exadel.sandbox.team2.dto.TokenDto;
import com.exadel.sandbox.team2.dto.UserDetailsDto;
import com.exadel.sandbox.team2.exception.ResourceNotFoundException;
import com.exadel.sandbox.team2.serivce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;



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

    public TokenDto login(String username) {

        Optional<User> optional = userService.findByUsername(username);
        if(optional.isPresent()){
            User user = optional.get();
            String refreshToken = UUID.randomUUID().toString();
            user.setRefreshToken(refreshToken);
            user.setTokenExpiryDate(LocalDate.now().plusMonths(6));
            userService.save(user);
            return jwtTokenProvider.generateJwtToken(username,refreshToken);
        }
        throw new ResourceNotFoundException(username);

    }

    public void logout() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal instanceof UserDetails ? ((UserDetails)principal).getUsername() : principal.toString();
        Optional<User> optional = userService.findByUsername(username);
        if(optional.isPresent()){
            User user = optional.get();
            user.setTokenExpiryDate(LocalDate.now());
            userService.save(user);
        }
    }

    public Optional<TokenDto> refresh(TokenDto tokenDto) {
        if(jwtTokenProvider.validateJwtToken(tokenDto.getAccessToken()) == 201){
            Optional<User> optional = userService.findByRefreshToken(tokenDto.getRefreshToken());
            if(optional.isPresent()){
                return Optional.of(login(optional.get().getEmail()));
            }
        }

        return Optional.empty();
    }
}
