package com.travely.travely.advice;

import com.travely.travely.dto.exception.ExceptionResponseDto;
import com.travely.travely.exception.AlreadyExistsEmailException;
import com.travely.travely.exception.NotFoundUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UsersExceptionControllerAdvice {
    private static final String FIELD = "USER";

    @ExceptionHandler(AlreadyExistsEmailException.class)
    public ResponseEntity<ExceptionResponseDto> emailAlreadyExists(AlreadyExistsEmailException exception) {
        log.debug("[EmailAlreadyExistsException]  {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponseDto.builder()
                        .field(FIELD)
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<ExceptionResponseDto> notFoundUser(NotFoundUserException exception) {
        log.info("[NotFoundUserException]  {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponseDto.builder()
                        .field(FIELD)
                        .message(exception.getMessage())
                        .build());
    }

}
