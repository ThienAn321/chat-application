package com.example.backendchat.service.dto.request;

import com.example.backendchat.constant.ErrorCodeConstant;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefreshTokenRequestDto {

    @NotNull(message = ErrorCodeConstant.REQUIRED_VALIDATE)
    private String refreshToken;
}
