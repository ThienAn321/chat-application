package com.example.backendchat.service;

import com.example.backendchat.service.dto.request.RegistrationRequestDto;

public interface RegistrationService {

    void register(RegistrationRequestDto registrationRequestDto);
}
