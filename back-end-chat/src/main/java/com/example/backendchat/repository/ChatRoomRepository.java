package com.example.backendchat.repository;

import com.example.backendchat.entity.ChatRoom;
import com.example.backendchat.service.dto.response.ChatRoomResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query(value = """
            SELECT
                cr.id AS chatRoomId,
                other_user.id AS userId,
                other_user.username as userName,
                other_user.avatar as avatar,
                latest_msg.user_id as senderId,
                latest_msg.content AS messageContent,
                latest_msg.is_image as isImage,
                latest_msg.created_at as createAt
            FROM
                chat_room cr
            INNER JOIN
                chat_room_users cru ON cr.id = cru.chat_room_id
            INNER JOIN
                user other_user ON other_user.id = cru.user_id
            LEFT JOIN (
                SELECT
                    m.chat_room_id,
                    m.content,
                    m.user_id,
                    m.is_image,
                    m.created_at
                FROM
                    message m
                INNER JOIN (
                    SELECT
                        chat_room_id,
                        MAX(created_at) AS latest_timestamp
                    FROM
                        message
                    GROUP BY
                        chat_room_id
                ) lm ON m.chat_room_id = lm.chat_room_id
                       AND m.created_at = lm.latest_timestamp
            ) latest_msg ON cr.id = latest_msg.chat_room_id
            WHERE
                cr.id IN (
                    SELECT
                        chat_room_id
                    FROM
                        chat_room_users
                    WHERE
                        user_id = :userId
                )
                AND other_user.id <> :userId
            ORDER BY
                cr.id, other_user.id
            """, nativeQuery = true)
    List<ChatRoomResponseDto> findChatRoomsWithLatestMessage(@Param("userId") Long userId);
}
