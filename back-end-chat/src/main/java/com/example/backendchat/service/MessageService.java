package com.example.backendchat.service;

import com.example.backendchat.service.dto.response.MessageResponseDto;
import com.example.backendchat.service.payload.ChatMessage;

import java.util.List;

public interface MessageService {

    List<MessageResponseDto> fetchMessagesByChatRoomId(Long chatRoomId);

    MessageResponseDto createMessage(ChatMessage chatMessage);
}
