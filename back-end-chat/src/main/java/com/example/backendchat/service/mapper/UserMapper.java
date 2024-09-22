package com.example.backendchat.service.mapper;

import com.example.backendchat.entity.User;
import com.example.backendchat.service.dto.response.UserResponseDto;
import com.example.backendchat.service.dto.response.UserSearchResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponseDto toUserResponseDto(User user);

    @Mapping(target = "isAdded", ignore = true)
    UserSearchResponseDto toUserSearchResponseDto(User user);
}
