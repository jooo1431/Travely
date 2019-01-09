package com.travely.travely.exception;

public class NotFoundReviewException extends RuntimeException {
    public NotFoundReviewException() {
        super("리뷰 내역이 없습니다.");
    }
}
