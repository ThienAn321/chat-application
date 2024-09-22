package com.example.backendchat.service;

import com.example.backendchat.service.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> fetchRandomUsers();
    UserResponseDto fetchUserDetails();
    List<UserResponseDto> searchByUserName(String username);
}
