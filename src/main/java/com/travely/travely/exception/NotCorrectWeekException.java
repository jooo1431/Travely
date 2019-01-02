package com.travely.travely.exception;

public class NotCorrectWeekException extends RuntimeException {
    public NotCorrectWeekException() {
        super("예약가능 요일이 아닙니다.");
    }
}
