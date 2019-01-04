package com.travely.travely.exception;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException() {
        super("해당 사용자를 찾을수 없습니다.");
    }
}
