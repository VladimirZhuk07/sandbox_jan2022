package com.exadel.sandbox.team2.controller;

import com.exadel.sandbox.team2.component.JwtTokenProvider;
import com.exadel.sandbox.team2.dto.LoginDto;
import com.exadel.sandbox.team2.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/authorization")
@RequiredArgsConstructor
public class AuthorizationRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtProvider;

    @PostMapping("/sign-in")
    public ResponseEntity<TokenDto> authenticateUser(@RequestBody LoginDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        TokenDto tokenDto = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDto> refresh(@RequestBody TokenDto tokenDto) {
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/sign-out")
    public ResponseEntity logout() {
        return ResponseEntity.ok().body(null);
    }
}
