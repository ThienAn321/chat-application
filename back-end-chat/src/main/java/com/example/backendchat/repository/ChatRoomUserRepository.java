package com.example.backendchat.repository;

import com.example.backendchat.entity.ChatRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {

    @Query("SELECT CASE WHEN COUNT(cr) > 0 THEN true ELSE false END " +
            "FROM ChatRoom cr " +
            "JOIN cr.chatRoomUsers cru1 " +
            "JOIN cr.chatRoomUsers cru2 " +
            "WHERE cru1.user.id = :currentUserId " +
            "AND cru2.user.id = :userId " +
            "AND cr.id = cru1.chatRoom.id " +
            "AND cr.id = cru2.chatRoom.id")
    boolean isUserAddedInChatRoomsWithCurrentUser(@Param("userId") Long userId, @Param("currentUserId") Long currentUserId);

}
