package com.example.backendchat.controller.user;

import com.example.backendchat.service.MessageService;
import com.example.backendchat.service.dto.response.MessageResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing messages.
 * This controller provides an API to fetch messages by chat room ID.
 */
@RestController
@RequestMapping("/api/v1/private/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    /**
     * API endpoint to fetch messages by chat room ID.
     *
     * @param chatRoomId the ID of the chat room
     * @return ResponseEntity with the list of MessageResponseDto and HTTP status 200
     */
    @Operation(summary = "API for fetch messages by chat room id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Fetch messages successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping
    public ResponseEntity<List<MessageResponseDto>> fetchMessagesByChatRoomId(@RequestParam("chatRoomId") Long chatRoomId) {
        return new ResponseEntity<>(messageService.fetchMessagesByChatRoomId(chatRoomId), HttpStatus.OK);
    }
}
