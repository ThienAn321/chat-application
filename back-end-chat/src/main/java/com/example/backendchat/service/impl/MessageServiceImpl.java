package com.example.backendchat.service.impl;

import com.example.backendchat.constant.ErrorCodeConstant;
import com.example.backendchat.entity.ChatRoom;
import com.example.backendchat.entity.Message;
import com.example.backendchat.entity.User;
import com.example.backendchat.exception.custom.DataNotFoundException;
import com.example.backendchat.repository.ChatRoomRepository;
import com.example.backendchat.repository.MessageRepository;
import com.example.backendchat.repository.UserRepository;
import com.example.backendchat.service.MessageService;
import com.example.backendchat.service.dto.response.MessageResponseDto;
import com.example.backendchat.service.mapper.MessageMapper;
import com.example.backendchat.service.payload.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service handles operations related to messages.
 */
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;

    /**
     * Fetches messages for a specific chat room by ID.
     *
     * @param chatRoomId ID of the chat room
     * @return List of MessageResponseDto containing details of the messages
     */
    @Override
    public List<MessageResponseDto> fetchMessagesByChatRoomId(Long chatRoomId) {
        return messageRepository.fetchMessagesByChatRoomId(chatRoomId);
    }

    /**
     * Creates a new message and saves it to the chat room.
     *
     * @param chatMessage Payload containing details of the message to be created
     * @return MessageResponseDto containing details of the created message
     */
    @Override
    public MessageResponseDto createMessage(ChatMessage chatMessage) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatMessage.getChatRoomId())
                .orElseThrow(() -> new DataNotFoundException(ErrorCodeConstant.getErrorCode(ErrorCodeConstant.DATA_NOT_FOUND, "chat_room")));
        User user = userRepository.findById(chatMessage.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        // Creates a new message
        Message message = new Message();
        message.setContent(chatMessage.getContent());
        message.setChatRoom(chatRoom);
        message.setUser(user);
        message.setIsImage(chatMessage.getIsImage());
        // Saves the message and converts it to a response DTO to send socket
        return messageMapper.toMessageResponseDto(messageRepository.save(message));
    }
}
