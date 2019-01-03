package com.travely.travely.advice;

import com.travely.travely.dto.exception.ExceptionResponseDto;
import com.travely.travely.exception.AuthenticationErrorException;
import com.travely.travely.exception.NotFoundReserveArchiveException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class OwnerExceptionControllerAdvice {
    private static final String FIELD = "owner";

    @ExceptionHandler(AuthenticationErrorException.class)
    public ResponseEntity<ExceptionResponseDto> authenticationError(AuthenticationErrorException exception) {
        log.info("[AuthenticationErrorException]  {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponseDto.builder()
                        .field(FIELD)
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(NotFoundReserveArchiveException.class)
    public ResponseEntity<ExceptionResponseDto> notFoundReserveArchive(NotFoundReserveArchiveException exception) {
        log.info("[NotFoundReserveArchiveException]  {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ExceptionResponseDto.builder()
                        .field(FIELD)
                        .message(exception.getMessage())
                        .build());
    }

}
