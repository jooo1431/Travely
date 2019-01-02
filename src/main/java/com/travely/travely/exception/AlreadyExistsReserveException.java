package com.travely.travely.exception;

public class AlreadyExistsReserveException extends RuntimeException {
    public AlreadyExistsReserveException() {
        super("이미 예약되어있습니다.");
    }
}
