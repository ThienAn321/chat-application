package com.example.backendchat.service.dto.response.impl;

import com.example.backendchat.service.dto.response.MessageResponseDto;
import lombok.Setter;

@Setter
public class MessageResponseDtoImpl implements MessageResponseDto {

    private String content;
    private Boolean isImage;
    private Long senderId;
    private String createdAt;

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public Long getSenderId() {
        return this.senderId;
    }

    @Override
    public Boolean getIsImage() {
        return this.isImage;
    }

    @Override
    public String getCreatedAt() {
        return this.createdAt;
    }
}
