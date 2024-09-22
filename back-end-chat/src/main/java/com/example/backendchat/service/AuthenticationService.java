package com.example.backendchat.service;

import com.example.backendchat.service.dto.request.AuthenticationRequestDto;
import com.example.backendchat.service.dto.request.RefreshTokenRequestDto;
import com.example.backendchat.service.dto.response.AuthenticationResponseDto;
import com.example.backendchat.service.dto.response.RefreshTokenResponseDto;

public interface AuthenticationService {

    AuthenticationResponseDto authenticate(AuthenticationRequestDto request);

    RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto request);
}
