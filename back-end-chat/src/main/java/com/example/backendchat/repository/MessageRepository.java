package com.example.backendchat.repository;

import com.example.backendchat.entity.Message;
import com.example.backendchat.service.dto.response.MessageResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT m.content AS content, m.is_image as isImage, m.user_id as senderId, m.created_at as createdAt " +
            "FROM message m WHERE m.chat_room_id = :chatRoomId " +
            "ORDER BY m.created_at",
            nativeQuery = true)
    List<MessageResponseDto> fetchMessagesByChatRoomId(@Param("chatRoomId") Long chatRoomId);
}
