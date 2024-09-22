package com.example.backendchat.service.mapper;

import com.example.backendchat.entity.Message;
import com.example.backendchat.service.dto.response.impl.MessageResponseDtoImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {

    @Mapping(target = "senderId", expression = "java(message.getUser().getId())")
    @Mapping(target = "isImage", expression = "java(message.getIsImage())")
    @Mapping(target = "createdAt", expression = "java(message.getCreatedAt().toString())")
    MessageResponseDtoImpl toMessageResponseDto(Message message);
}
