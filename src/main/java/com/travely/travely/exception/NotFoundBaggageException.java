package com.travely.travely.exception;

public class NotFoundBaggageException extends RuntimeException {
    public NotFoundBaggageException() {
        super("짐을 찾을 수 없습니다.");
    }
}
