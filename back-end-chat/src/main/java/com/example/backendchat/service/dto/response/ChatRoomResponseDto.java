package com.example.backendchat.service.dto.response;

public interface ChatRoomResponseDto {

    Long getChatRoomId();
    Long getUserId();
    String getUserName();
    String getAvatar();
    Integer getSenderId();
    String getMessageContent();
    Boolean getIsImage();
    String getCreateAt();
}
