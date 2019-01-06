package com.travely.travely.exception;

public class NotCorrectTimeException extends RuntimeException {
    public NotCorrectTimeException() {
        super("예약가능 시간이 아닙니다.");
    }
}
