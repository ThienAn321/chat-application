package com.example.backendchat.service.dto.request;

import com.example.backendchat.constant.ErrorCodeConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequestDto {

    @Email(message = ErrorCodeConstant.REQUIRED_VALIDATE)
    @Size(min = 1, max = 255, message = ErrorCodeConstant.INVALID_LENGTH)
    private String email;

    @NotNull(message = ErrorCodeConstant.REQUIRED_VALIDATE)
    @Size(min = 1, max = 255, message = ErrorCodeConstant.INVALID_LENGTH)
    private String username;

    @NotNull(message = ErrorCodeConstant.REQUIRED_VALIDATE)
    @Size(min = 1, max = 255, message = ErrorCodeConstant.INVALID_LENGTH)
    private String password;

    private String avatar;
}
