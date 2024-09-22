package com.example.backendchat.service;

import com.example.backendchat.service.dto.request.ChatRoomRequestDto;
import com.example.backendchat.service.dto.response.ChatRoomResponseDto;

import java.util.List;

public interface ChatRoomService {

    List<ChatRoomResponseDto> fetchChatRoom();

    void createChatRoom(ChatRoomRequestDto chatRoomRequestDto);
}
