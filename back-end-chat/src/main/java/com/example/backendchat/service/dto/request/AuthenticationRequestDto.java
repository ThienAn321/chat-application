package com.example.backendchat.service.dto.request;

import com.example.backendchat.constant.ErrorCodeConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthenticationRequestDto {

    @NotNull(message = ErrorCodeConstant.REQUIRED_VALIDATE)
    @Email
    private String email;

    @NotNull(message = ErrorCodeConstant.REQUIRED_VALIDATE)
    private String password;
}
