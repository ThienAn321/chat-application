package com.example.backendchat.service.dto.response;

public interface MessageResponseDto {

    String getContent();
    Long getSenderId();
    Boolean getIsImage();
    String getCreatedAt();
}
