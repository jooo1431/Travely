package com.travely.travely.exception;

public class NotOpenStoreException extends RuntimeException {
    public NotOpenStoreException() {
        super("예약 불가능한 가게입니다.");
    }
}
