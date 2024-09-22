package com.example.backendchat.controller.publics;

import com.example.backendchat.service.AuthenticationService;
import com.example.backendchat.service.dto.request.AuthenticationRequestDto;
import com.example.backendchat.service.dto.request.RefreshTokenRequestDto;
import com.example.backendchat.service.dto.response.AuthenticationResponseDto;
import com.example.backendchat.service.dto.response.RefreshTokenResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling authentication and token refresh requests.
 * This controller provides APIs for user login and refreshing JWT tokens.
 */
@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * API endpoint for user login.
     *
     * @param request the login request containing the user's credentials
     * @return a ResponseEntity containing the authentication response with JWT tokens
     */
    @Operation(summary = "API for user authentication")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Login successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody @Valid AuthenticationRequestDto request) {
        return new ResponseEntity<>(authenticationService.authenticate(request), HttpStatus.OK);
    }

    /**
     * API endpoint for refreshing the JWT token.
     *
     * @param request the refresh token request containing the refresh token
     * @return a ResponseEntity containing the new JWT access token and refresh token
     */
    @Operation(summary = "API for refresh token")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Refresh token successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDto> refreshToken(@RequestBody @Valid RefreshTokenRequestDto request) {
        return new ResponseEntity<>(authenticationService.refreshToken(request), HttpStatus.OK);
    }
}
