package com.example.backendchat.service.dto.request;

import com.example.backendchat.constant.ErrorCodeConstant;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChatRoomRequestDto {

    @NotNull(message = ErrorCodeConstant.REQUIRED_VALIDATE)
    private Long userId;
}
