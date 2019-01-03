package com.travely.travely.exception;

public class NotFoundStoringException extends RuntimeException {
    public NotFoundStoringException() {
        super("보관내역이 없습니다.");
    }
}
