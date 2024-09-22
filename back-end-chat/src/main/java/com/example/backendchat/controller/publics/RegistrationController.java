package com.example.backendchat.controller.publics;

import com.example.backendchat.service.RegistrationService;
import com.example.backendchat.service.dto.request.RegistrationRequestDto;
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
 * REST controller for handling user registration requests.
 * This controller provides an API endpoint for user registration.
 */
@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    /**
     * API endpoint for user registration.
     *
     * @param request the registration request containing user details
     * @return a ResponseEntity with HTTP status 201 (Created) if successful
     */
    @Operation(summary = "API for user registration")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Register successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "409", description = "User existed"),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegistrationRequestDto request) {
        registrationService.register(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
