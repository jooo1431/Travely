package com.travely.travely.exception;

public class NotCorrectTimeException extends RuntimeException {
    public NotCorrectTimeException(final String msg) {
        super("예약가능 시간이 아닙니다.\n" +
                msg);
    }
}
