package com.example.backendchat.controller.user;

import com.example.backendchat.service.ChatRoomService;
import com.example.backendchat.service.dto.request.ChatRoomRequestDto;
import com.example.backendchat.service.dto.response.ChatRoomResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing chat rooms.
 * This controller provides APIs to fetch and create chat rooms.
 */
@RestController
@RequestMapping("/api/v1/private/chatrooms")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    /**
     * API endpoint to fetch all chat rooms with the latest message.
     *
     * @return ResponseEntity with the list of ChatRoomResponseDto and HTTP status 200.
     */
    @Operation(summary = "API for fetch chat room with latest message")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Search successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping
    public ResponseEntity<List<ChatRoomResponseDto>> fetchChatRoom() {
        return new ResponseEntity<>(chatRoomService.fetchChatRoom(), HttpStatus.OK);
    }

    /**
     * API endpoint to create a new chat room.
     *
     * @param request the chat room request containing necessary information
     * @return ResponseEntity with HTTP status 201 if the chat room is created successfully
     */
    @Operation(summary = "API for create chat room")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Create chat room successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PostMapping
    public ResponseEntity<Void> createChatRoom(@RequestBody @Valid ChatRoomRequestDto request) {
        chatRoomService.createChatRoom(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
