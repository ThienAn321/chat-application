package com.example.backendchat.service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenResponseDto {

    private String accessToken;
}
