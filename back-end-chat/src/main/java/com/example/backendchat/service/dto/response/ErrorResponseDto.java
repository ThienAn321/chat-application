package com.example.backendchat.service.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponseDto {
    
    private HttpStatus httpStatus;
    private String message;
    private LocalDateTime errorTime;
}
