package com.example.backendchat.service.impl;

import com.example.backendchat.constant.ErrorCodeConstant;
import com.example.backendchat.exception.custom.DataNotFoundException;
import com.example.backendchat.repository.UserRepository;
import com.example.backendchat.security.jwt.JwtTokenProvider;
import com.example.backendchat.security.model.CustomUserDetails;
import com.example.backendchat.service.AuthenticationService;
import com.example.backendchat.service.dto.request.AuthenticationRequestDto;
import com.example.backendchat.service.dto.request.RefreshTokenRequestDto;
import com.example.backendchat.service.dto.response.AuthenticationResponseDto;
import com.example.backendchat.service.dto.response.RefreshTokenResponseDto;
import com.example.backendchat.service.mapper.AuthenticationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Service handles user authentication.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationMapper authenticationMapper;
    private final AuthenticationManager authenticationManager;

    /**
     * Authenticates a user and generates an access token and a refresh token.
     *
     * @param request The authentication request containing email and password
     * @return AuthenticationResponseDto containing user ID, access token, refresh token, and token expiration time
     */
    @Override
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        // Authenticate the user using the provided email and password
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        // Fetch user details from the repository
        CustomUserDetails userDetails = userRepository.findByEmail(request.getEmail())
                .map(authenticationMapper::toUserDetail)
                .orElseThrow(() -> new DataNotFoundException(ErrorCodeConstant.getErrorCode(ErrorCodeConstant.DATA_NOT_FOUND, "user")));
        // Generate access token and refresh token
        String token = jwtTokenProvider.generateToken(userDetails, false);
        String refreshToken = jwtTokenProvider.generateToken(userDetails, true);
        log.info("User id {} login successfully", userDetails.getId());
        return AuthenticationResponseDto.builder()
                .userId(userDetails.getId())
                .token(token)
                .refreshToken(refreshToken)
                .expiredTime(jwtTokenProvider.extractExpiration(token))
                .build();
    }

    /**
     * Generates a new access token from a valid refresh token.
     *
     * @param request The refresh token request containing the refresh token
     * @return RefreshTokenResponseDto containing the new access token
     */
    @Override
    public RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto request) {
        return RefreshTokenResponseDto.builder()
                .accessToken(jwtTokenProvider.generateAccessTokenFromRefreshToken(request.getRefreshToken()))
                .build();
    }
}
