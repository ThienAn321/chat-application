package com.example.backendchat.service.impl;

import com.example.backendchat.constant.ErrorCodeConstant;
import com.example.backendchat.entity.User;
import com.example.backendchat.exception.custom.EmailAlreadyExists;
import com.example.backendchat.repository.UserRepository;
import com.example.backendchat.service.RegistrationService;
import com.example.backendchat.service.dto.request.RegistrationRequestDto;
import com.example.backendchat.service.mapper.RegistrationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service that handles user registration.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final RegistrationMapper registrationMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user by saving their details to the database.
     *
     * @param registrationRequestDto DTO containing user registration details
     * @throws EmailAlreadyExists if the email is already registered
     */
    @Override
    public void register(RegistrationRequestDto registrationRequestDto) {
        if (userRepository.findByEmail(registrationRequestDto.getEmail()).isPresent()) {
            throw new EmailAlreadyExists(ErrorCodeConstant.getErrorCode(ErrorCodeConstant.EMAIL_ALREADY_EXISTS));
        }
        User user = registrationMapper.toEntity(registrationRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("User {} register successfully", user.getEmail());
    }
}
