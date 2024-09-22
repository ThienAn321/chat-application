package com.example.backendchat.service.mapper;

import com.example.backendchat.entity.User;
import com.example.backendchat.service.dto.request.RegistrationRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegistrationMapper {

    User toEntity(RegistrationRequestDto registrationRequestDto);
}
