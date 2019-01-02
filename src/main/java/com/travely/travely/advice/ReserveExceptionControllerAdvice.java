package com.travely.travely.advice;

import com.travely.travely.dto.exception.ExceptionResponseDto;
import com.travely.travely.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ReserveExceptionControllerAdvice {
    private static final String FIELD = "reserve";

    @ExceptionHandler(NotFoundBaggageException.class)
    public ResponseEntity<ExceptionResponseDto> notFoundBaggage(NotFoundBaggageException exception) {
        log.debug("[NotFoundBaggageException]  {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponseDto.builder()
                        .field(FIELD)
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(ExceedCapacityException.class)
    public ResponseEntity<ExceptionResponseDto> exceedCapcity(ExceedCapacityException exception) {
        log.debug("[ExceedCapacityException]  {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponseDto.builder()
                        .field(FIELD)
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(AlreadyExistsReserveException.class)
    public ResponseEntity<ExceptionResponseDto> reserveAlreadyExists(AlreadyExistsReserveException exception) {
        log.debug("[AlreadyExistsReserveException]  {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponseDto.builder()
                        .field(FIELD)
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(NotCorrectTimeException.class)
    public ResponseEntity<ExceptionResponseDto> notCorrectTime(NotCorrectTimeException exception) {
        log.debug("[NotCorrectTimeException]  {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponseDto.builder()
                        .field(FIELD)
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(NotCorrectWeekException.class)
    public ResponseEntity<ExceptionResponseDto> notCorrectWeek(NotCorrectWeekException exception) {
        log.debug("[NotCorrectWeekException]  {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponseDto.builder()
                        .field(FIELD)
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(NotFoundReserveException.class)
    public ResponseEntity<ExceptionResponseDto> notFoundReserve(NotFoundReserveException exception) {
        log.debug("[NotFoundReserveException]  {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponseDto.builder()
                        .field(FIELD)
                        .message(exception.getMessage())
                        .build());
    }
}
