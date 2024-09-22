package com.example.backendchat.constant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.MessageFormat;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorCodeConstant {

    public static final String DATA_NOT_FOUND = "error.{0}.not-found";
    public static final String EMAIL_ALREADY_EXISTS = "error.email.already-exists";
    public static final String REQUIRED_VALIDATE = "error.required";
    public static final String INVALID_LENGTH = "error.length";
    public static final String INVALID_VALIDATE = "error.invalid";
    public static final String INVALID_REFRESH_TOKEN = "error.invalid.refresh-token";

    public static String getErrorCode(String code, Object... args) {
        return MessageFormat.format(code, args);
    }
}
