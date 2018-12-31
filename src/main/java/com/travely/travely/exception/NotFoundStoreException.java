package com.travely.travely.exception;

public class NotFoundStoreException extends RuntimeException {
    public NotFoundStoreException() {
        super("해당 상가를 찾을 수 없습니다.");
    }
}
