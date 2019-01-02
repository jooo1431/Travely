package com.travely.travely.exception;

public class ExceedCapacityException extends RuntimeException {
    public ExceedCapacityException() {
        super("업체의 수용량을 초과하였습니다.");
    }
}
