package com.travely.travely.advice;

import com.travely.travely.dto.exception.ExceptionResponseDto;
import com.travely.travely.exception.NotFoundReviewException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ReviewExceptionControllerAdvice {
    private static final String FIELD = "review";

    @ExceptionHandler(NotFoundReviewException.class)
    public ResponseEntity<ExceptionResponseDto> notFoundReview(NotFoundReviewException exception) {
        log.info("[NotFoundReviewException]  {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ExceptionResponseDto.builder()
                        .field(FIELD)
                        .message(exception.getMessage())
                        .build());
    }


}
