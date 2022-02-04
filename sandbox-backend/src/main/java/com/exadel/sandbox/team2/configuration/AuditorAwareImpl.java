package com.exadel.sandbox.team2.configuration;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        //String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return "System"; //Optional.of(loggedInUsername);
    }
}
