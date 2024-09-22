package com.example.backendchat.repository;

import com.example.backendchat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :userName, '%')) AND u.id != :currentUserId")
    List<User> findLikeUserName(@Param("userName") String userName, @Param("currentUserId") Long currentUserId);

    @Query("SELECT u FROM User u WHERE u.id != :currentUserId AND u.id NOT IN (SELECT cru.user.id FROM ChatRoomUser cru " +
            "WHERE cru.chatRoom.id IN (SELECT cr.id FROM ChatRoom cr WHERE cr.id IN " +
            "(SELECT cru.chatRoom.id FROM ChatRoomUser cru WHERE cru.user.id = :currentUserId)))")
    List<User> findUsersExcludingCurrentUser(@Param("currentUserId") Long currentUserId);
}
