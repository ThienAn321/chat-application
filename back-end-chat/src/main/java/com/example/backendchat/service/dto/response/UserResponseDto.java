package com.example.backendchat.service.dto.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class UserResponseDto {

    private Long id;
    private String username;
    private String email;
    private String avatar;
}
