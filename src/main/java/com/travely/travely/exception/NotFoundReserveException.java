package com.travely.travely.exception;

public class NotFoundReserveException extends RuntimeException {
    public NotFoundReserveException() {
        super("예약내역이 없습니다.");
    }
}
