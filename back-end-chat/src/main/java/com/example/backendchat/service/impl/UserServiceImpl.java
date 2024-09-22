package com.example.backendchat.service.impl;

import com.example.backendchat.constant.ErrorCodeConstant;
import com.example.backendchat.entity.User;
import com.example.backendchat.exception.custom.DataNotFoundException;
import com.example.backendchat.repository.ChatRoomUserRepository;
import com.example.backendchat.repository.UserRepository;
import com.example.backendchat.security.handler.SecurityContextHandler;
import com.example.backendchat.service.UserService;
import com.example.backendchat.service.dto.response.UserResponseDto;
import com.example.backendchat.service.dto.response.UserSearchResponseDto;
import com.example.backendchat.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service that handles user-related operations.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final UserMapper userMapper;

    /**
     * Fetches a random list of 5 users, excluding the current user.
     *
     * @return List of UserResponseDto representing random users
     * @throws DataNotFoundException if the current user cannot be found
     */
    @Override
    public List<UserResponseDto> fetchRandomUsers() {
        List<User> users = SecurityContextHandler.getCurrentUserId()
                .map(userRepository::findUsersExcludingCurrentUser)
                .orElseThrow(() -> new DataNotFoundException(ErrorCodeConstant.getErrorCode(ErrorCodeConstant.DATA_NOT_FOUND, "user")));

        // Shuffle the list and select the first 5 users
        Collections.shuffle(users);
        return users.stream()
                .limit(5)
                .map(userMapper::toUserResponseDto)
                .toList();
    }

    /**
     * Fetches the details of the current user.
     *
     * @return UserResponseDto containing the current user's details
     * @throws DataNotFoundException if the current user cannot be found
     */
    @Override
    public UserResponseDto fetchUserDetails() {
        return SecurityContextHandler.getCurrentUserId()
                .flatMap(userRepository::findById)
                .map(userMapper::toUserResponseDto)
                .orElseThrow(() -> new DataNotFoundException(ErrorCodeConstant.getErrorCode(ErrorCodeConstant.DATA_NOT_FOUND, "user")));
    }

    /**
     * Searches for users by username, and checks if they are added in chat rooms with the current user.
     *
     * @param username the username to search for
     * @return List of UserSearchResponseDto representing the search results
     * @throws DataNotFoundException if the current user cannot be found
     */
    @Override
    public List<UserResponseDto> searchByUserName(String username) {
        Long currentUserId = SecurityContextHandler.getCurrentUserId()
                .orElseThrow(() -> new DataNotFoundException(ErrorCodeConstant.getErrorCode(ErrorCodeConstant.DATA_NOT_FOUND, "user")));
        List<User> users = userRepository.findLikeUserName(username, currentUserId);
        return users.stream()
                .map(user -> {
                    boolean isAdded = chatRoomUserRepository.isUserAddedInChatRoomsWithCurrentUser(currentUserId, user.getId());
                    UserSearchResponseDto userSearchResponseDto = userMapper.toUserSearchResponseDto(user);
                    userSearchResponseDto.setAdded(isAdded);
                    return userSearchResponseDto;
                })
                .collect(Collectors.toList());
    }
}
