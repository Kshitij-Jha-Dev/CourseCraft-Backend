package com.onlineCourse.eduhub.security;

import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    public Optional<String> getCurrentUserEmail() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()
                || auth instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        return Optional.of(auth.getName());
    }
    
    public boolean isAdmin() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) return false;

        return auth.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}