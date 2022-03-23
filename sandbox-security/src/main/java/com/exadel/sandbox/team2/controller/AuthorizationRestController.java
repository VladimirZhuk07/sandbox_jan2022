package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.component.JwtTokenProvider;
import com.exadel.sandbox.team2.dto.LoginDto;
import com.exadel.sandbox.team2.dto.TokenDto;
import com.exadel.sandbox.team2.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/authorization")
@RequiredArgsConstructor
public class AuthorizationRestController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/sign-in")
    public ResponseEntity<TokenDto> authenticateUser(@RequestBody LoginDto loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        TokenDto tokenDto = userDetailsService.login(loginRequest.getUsername());
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDto> refresh(@RequestBody TokenDto tokenDto) {
        Optional<TokenDto> optional = userDetailsService.refresh(tokenDto);
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/sign-out")
    public ResponseEntity logout() {
        userDetailsService.logout();
        return ResponseEntity.ok().build();
    }
}
