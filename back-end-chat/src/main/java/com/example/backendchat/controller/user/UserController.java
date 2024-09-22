package com.example.backendchat.controller.user;

import com.example.backendchat.service.UserService;
import com.example.backendchat.service.dto.response.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing user-related requests.
 * This controller provides APIs for fetching user details, random users, and searching users by username.
 */
@RestController
@RequestMapping("/api/v1/private/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * API endpoint to fetch the current user's details.
     *
     * @return ResponseEntity with UserResponseDto and HTTP status 200
     */
    @Operation(summary = "API for fetch user details")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Fetch messages successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping
    public ResponseEntity<UserResponseDto> fetchUserDetails() {
        return new ResponseEntity<>(userService.fetchUserDetails(), HttpStatus.OK);
    }

    /**
     * API endpoint to fetch 5 random users.
     *
     * @return ResponseEntity with a list of UserResponseDto and HTTP status 200
     */
    @Operation(summary = "API for fetch random 5 users")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Fetch users successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping("/random")
    public ResponseEntity<List<UserResponseDto>> fetchRandomUsers() {
        return new ResponseEntity<>(userService.fetchRandomUsers(), HttpStatus.OK);
    }

    /**
     * API endpoint to search for users by username.
     *
     * @param username the username to search for
     * @return ResponseEntity with a list of UserResponseDto and HTTP status 200
     */
    @Operation(summary = "API for search by username")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Fetch users successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping("/search")
    public ResponseEntity<List<UserResponseDto>> searchByUserName(@RequestParam("username") String username) {
        return new ResponseEntity<>(userService.searchByUserName(username), HttpStatus.OK);
    }
}
