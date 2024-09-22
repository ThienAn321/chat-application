package com.example.backendchat.security.service;

import com.example.backendchat.constant.ErrorCodeConstant;
import com.example.backendchat.exception.custom.DataNotFoundException;
import com.example.backendchat.repository.UserRepository;
import com.example.backendchat.service.mapper.AuthenticationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of UserDetailsService to load user-specific data for authentication.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthenticationMapper authenticationMapper;

    /**
     * Loads user details by username (email in this case).
     *
     * @param username The username (email) of the user
     * @return UserDetails containing user information
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user by email from the repository
        return userRepository.findByEmail(username)
                .map(authenticationMapper::toUserDetail)
                .orElseThrow(() -> new DataNotFoundException(ErrorCodeConstant.getErrorCode(ErrorCodeConstant.DATA_NOT_FOUND, "user")));
    }
}
