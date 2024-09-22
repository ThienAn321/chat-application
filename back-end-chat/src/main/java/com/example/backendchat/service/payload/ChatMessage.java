package com.example.backendchat.service.payload;

import lombok.Data;

@Data
public class ChatMessage {

    private Long chatRoomId;
    private Long senderId;
    private Long recipientId;
    private String content;
    private Boolean isImage;
}
