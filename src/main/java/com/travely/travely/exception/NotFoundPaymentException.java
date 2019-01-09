package com.travely.travely.exception;

public class NotFoundPaymentException extends RuntimeException {
    public NotFoundPaymentException() {
        super("해당 결제 내역을 찾을 수 없습니다.");
    }
}
