package com.example.backendchat.service.impl;

import com.example.backendchat.constant.ErrorCodeConstant;
import com.example.backendchat.entity.ChatRoom;
import com.example.backendchat.entity.ChatRoomUser;
import com.example.backendchat.entity.User;
import com.example.backendchat.exception.custom.DataNotFoundException;
import com.example.backendchat.repository.ChatRoomRepository;
import com.example.backendchat.repository.ChatRoomUserRepository;
import com.example.backendchat.repository.UserRepository;
import com.example.backendchat.security.handler.SecurityContextHandler;
import com.example.backendchat.service.ChatRoomService;
import com.example.backendchat.service.dto.request.ChatRoomRequestDto;
import com.example.backendchat.service.dto.response.ChatRoomResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service handles operations related to chat rooms.
 */
@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Fetches chat rooms for the current user with the latest message in each room.
     *
     * @return List of ChatRoomResponseDto containing details of the chat rooms
     */
    @Override
    public List<ChatRoomResponseDto> fetchChatRoom() {
        Long userId = getUser(SecurityContextHandler.getCurrentUserId()).getId();
        return chatRoomRepository.findChatRoomsWithLatestMessage(userId);
    }

    /**
     * Creates a new chat room and adds the current user and the requested user to it.
     *
     * @param chatRoomRequestDto Request DTO containing details for creating the chat room
     */
    @Override
    public void createChatRoom(ChatRoomRequestDto chatRoomRequestDto) {
        ChatRoom savedChatRoom = chatRoomRepository.save(new ChatRoom());
        // Fetches the current user and the user specified in the request
        User currentUser = getUser(SecurityContextHandler.getCurrentUserId());
        User requestedUser = getUser(Optional.of(chatRoomRequestDto.getUserId()));
        saveChatRoomUser(savedChatRoom, currentUser);
        saveChatRoomUser(savedChatRoom, requestedUser);
        // Send socket to requestedUser about the new chat room
        messagingTemplate.convertAndSend("/topic/updateChatRoom/" + requestedUser.getId(), "update");
    }

    /**
     * Saves a user to a chat room.
     *
     * @param chatRoom The chat room to associate the user with
     * @param user The user to be added to the chat room
     */
    private void saveChatRoomUser(ChatRoom chatRoom, User user) {
        ChatRoomUser chatRoomUser = ChatRoomUser.builder()
                .chatRoom(chatRoom)
                .user(user)
                .build();
        chatRoomUserRepository.save(chatRoomUser);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId Optional user ID
     * @return The User entity
     * @throws DataNotFoundException if the user is not found
     */
    private User getUser(Optional<Long> userId) {
        return userId.flatMap(userRepository::findById)
                .orElseThrow(() -> new DataNotFoundException(ErrorCodeConstant.getErrorCode(ErrorCodeConstant.DATA_NOT_FOUND, "user")));
    }
}
