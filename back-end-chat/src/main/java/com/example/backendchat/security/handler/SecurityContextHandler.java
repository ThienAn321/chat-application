package com.example.backendchat.security.handler;

import com.example.backendchat.security.model.CustomUserDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Utility class to handle operations related to the security context.
 * Provides methods to extract information from the current security context.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityContextHandler {

    /**
     * Retrieves the current authenticated user's ID from the security context.
     *
     * @return An Optional containing the user ID if the user is authenticated and has a CustomUserDetails object otherwise, an empty Optional.
     */
    public static Optional<Long> getCurrentUserId() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .flatMap(principal -> {
                    if (principal instanceof CustomUserDetails) {
                        return Optional.of(((CustomUserDetails) principal).getId());
                    }
                    return Optional.empty();
                });
    }
}
