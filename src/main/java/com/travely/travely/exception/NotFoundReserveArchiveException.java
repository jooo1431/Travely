package com.travely.travely.exception;

public class NotFoundReserveArchiveException extends RuntimeException {
    public NotFoundReserveArchiveException() {
        super("예약 및 보관 내역이 없습니다.");
    }
}
