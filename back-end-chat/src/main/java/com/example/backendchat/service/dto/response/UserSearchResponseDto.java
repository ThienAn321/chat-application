package com.example.backendchat.service.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserSearchResponseDto extends UserResponseDto {

    private boolean isAdded;
}
