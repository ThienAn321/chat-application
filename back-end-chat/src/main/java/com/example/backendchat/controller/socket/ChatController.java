package com.example.backendchat.controller.socket;

import com.example.backendchat.service.MessageService;
import com.example.backendchat.service.dto.response.MessageResponseDto;
import com.example.backendchat.service.payload.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        MessageResponseDto messageDto = messageService.createMessage(chatMessage);
        String recipientDestination = "/user/" + chatMessage.getRecipientId() + "/queue/messages";
        String senderDestination = "/user/" + chatMessage.getSenderId() + "/queue/messages";

        // Send the message to both users
        messagingTemplate.convertAndSend(recipientDestination, messageDto);
        messagingTemplate.convertAndSend(senderDestination, messageDto);
    }
}
