package com.example.backendchat.service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class AuthenticationResponseDto {

    private Long userId;
    private String token;
    private String refreshToken;
    private Date expiredTime;
}
