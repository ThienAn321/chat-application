package com.example.backendchat.exception.handler;

import com.example.backendchat.exception.custom.DataNotFoundException;
import com.example.backendchat.exception.custom.EmailAlreadyExists;
import com.example.backendchat.service.dto.response.ErrorResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Global exception handler for the application.
 * This class provides a mechanism to handle exceptions globally across all controllers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle validation errors when arguments annotated with @Valid fail.
     *
     * @param ex         MethodArgumentNotValidException containing validation errors
     * @param headers    HTTP headers
     * @param status     HTTP status code
     * @param webRequest WebRequest
     * @return ResponseEntity with a map containing validation errors and HTTP status
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest webRequest) {
        Map<String, Object> validationErrors = new LinkedHashMap<>();
        Map<String, String> errors = new LinkedHashMap<>();
        validationErrors.put("httpStatus", String.valueOf(status.value()));
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            errors.put(fieldName, validationMsg);
        });
        validationErrors.put("errors", errors);
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle any general exceptions that are not specifically caught.
     *
     * @param exception The thrown exception
     * @return ResponseEntity with error details and HTTP 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception) {
        ErrorResponseDto errorResponseDTO = ErrorResponseDto.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .errorTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle DataNotFoundException which occurs when data is not found.
     *
     * @param exception The thrown DataNotFoundException
     * @return ResponseEntity with error details and HTTP 404 status
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleDataNotFoundException(DataNotFoundException exception) {
        ErrorResponseDto errorResponseDTO = ErrorResponseDto.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .errorTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle EmailAlreadyExists exception when a user tries to register with an existing email.
     *
     * @param exception The thrown EmailAlreadyExists exception
     * @return ResponseEntity with error details and HTTP 409 status
     */
    @ExceptionHandler(EmailAlreadyExists.class)
    public ResponseEntity<ErrorResponseDto> handleEmailAlreadyExists(EmailAlreadyExists exception) {
        ErrorResponseDto errorResponseDTO = ErrorResponseDto.builder()
                .httpStatus(HttpStatus.CONFLICT)
                .message(exception.getMessage())
                .errorTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.CONFLICT);
    }

    /**
     * Handle IllegalArgumentException, typically used for invalid argument cases.
     *
     * @param exception The thrown IllegalArgumentException
     * @return ResponseEntity with error details and HTTP 400 status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException exception) {
        ErrorResponseDto errorResponseDTO = ErrorResponseDto.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .errorTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle ExpiredJwtException, which is thrown when a JWT token is expired.
     *
     * @param exception The thrown ExpiredJwtException
     * @return ResponseEntity with error details and HTTP 401 status
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponseDto> handleExpiredJwtException(ExpiredJwtException exception) {
        ErrorResponseDto errorResponseDTO = ErrorResponseDto.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .message(exception.getMessage())
                .errorTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.UNAUTHORIZED);
    }
}
