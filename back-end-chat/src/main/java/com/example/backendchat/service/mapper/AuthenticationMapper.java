package com.example.backendchat.service.mapper;

import com.example.backendchat.entity.User;
import com.example.backendchat.security.model.CustomUserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthenticationMapper {

    CustomUserDetails toUserDetail(User user);
}
